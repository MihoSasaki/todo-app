package com.todoapp.backend.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TodoItem(
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val completed: Boolean = false,
    val createdAt: String = LocalDateTime.now().toString(),
    val updatedAt: String = LocalDateTime.now().toString()
)

@Serializable
data class TodoItemRequest(
    val title: String,
    val description: String? = null,
    val completed: Boolean = false
)