package com.example.todov5

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ToDoRepository(private val dao: ToDoDao) {
    val allItems: LiveData<List<ToDoItem>> =
        dao.getAll().map { list -> list.map { it.toToDoItem() } }

    suspend fun insert(item: ToDoItem) {
        withContext(Dispatchers.IO) {
            dao.insert(item.toEntity())
        }
    }

    suspend fun update(item: ToDoItem) {
        withContext(Dispatchers.IO) {
            dao.update(item.toEntity())
        }
    }

    suspend fun delete(item: ToDoItem) {
        withContext(Dispatchers.IO) {
            dao.delete(item.toEntity())
        }
    }

    suspend fun deleteById(id: Int) {
        withContext(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}

fun ToDoItemEntity.toToDoItem(): ToDoItem = ToDoItem(
    id = id,
    title = title,
    description = description,
    priority = Priority.valueOf(priority),
    isDone = isDone
)

fun ToDoItem.toEntity(): ToDoItemEntity = ToDoItemEntity(
    id = id,
    title = title,
    description = description,
    priority = priority.name,
    isDone = isDone
)
