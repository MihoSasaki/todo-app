package com.todoapp.android.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.InternalSerializationApi
import java.time.LocalDateTime

@OptIn(InternalSerializationApi::class)
@Serializable
data class TodoItem(
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val completed: Boolean = false,
    val createdAt: String = LocalDateTime.now().toString(),
    val updatedAt: String = LocalDateTime.now().toString()
)
