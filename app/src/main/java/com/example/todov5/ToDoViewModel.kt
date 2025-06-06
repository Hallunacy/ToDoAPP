package com.example.todov5

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ToDoRepository
    val tasks: LiveData<List<ToDoItem>>

    init {
        val db = AppDatabase.getDatabase(application)
        repository = ToDoRepository(db.toDoDao())
        tasks = repository.allItems
    }

    fun addTask(task: ToDoItem) {
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    fun updateTask(updated: ToDoItem) {
        viewModelScope.launch {
            repository.update(updated)
        }
    }

    fun removeTask(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}
