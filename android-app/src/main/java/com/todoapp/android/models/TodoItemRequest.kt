package com.todoapp.android.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
@Serializable
data class TodoItemRequest(
    val title: String,
    val description: String? = null,
    val completed: Boolean = false
)
