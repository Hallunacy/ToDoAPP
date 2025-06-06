package com.example.todov5

// Enum for task priority
enum class Priority {
    HIGH, MEDIUM, LOW
}

// Data class for a to-do item
// id: unique identifier for each task
// title: task title
// description: optional details
// priority: task priority
// isDone: whether the task is completed

data class ToDoItem(
    val id: Int,
    val title: String,
    val description: String? = null,
    val priority: Priority = Priority.LOW,
    var isDone: Boolean = false
)

