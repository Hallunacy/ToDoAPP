package com.example.todov5

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

// ViewModel holds the list of ToDoItems and exposes it as LiveData
class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ToDoRepository
    val tasks: LiveData<List<ToDoItem>>

    init {
        val db = AppDatabase.getDatabase(application)
        repository = ToDoRepository(db.toDoDao())
        tasks = repository.allItems
    }

    // Adds a new task to the list
    fun addTask(task: ToDoItem) {
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    // Updates a task (e.g., mark as done)
    fun updateTask(updated: ToDoItem) {
        viewModelScope.launch {
            repository.update(updated)
        }
    }

    // (Optional) Remove a task
    fun removeTask(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}
