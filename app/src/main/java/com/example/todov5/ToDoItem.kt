package com.example.todov5

enum class Priority {
    HIGH, MEDIUM, LOW
}

data class ToDoItem(
    val id: Int,
    val title: String,
    val description: String? = null,
    val priority: Priority = Priority.LOW,
    var isDone: Boolean = false
)
