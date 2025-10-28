package com.todoapp.android.network

import com.todoapp.android.models.TodoItem
import com.todoapp.android.models.TodoItemRequest
import retrofit2.http.*

interface TodoApiService {
    @GET("api/todos")
    suspend fun getAllTodos(): List<TodoItem>

    @GET("api/todos/{id}")
    suspend fun getTodo(@Path("id") id: Int): TodoItem

    @POST("api/todos")
    suspend fun createTodo(@Body todo: TodoItemRequest): TodoItem

    @PUT("api/todos/{id}")
    suspend fun updateTodo(@Path("id") id: Int, @Body todo: TodoItem): TodoItem

    @DELETE("api/todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int)
}
