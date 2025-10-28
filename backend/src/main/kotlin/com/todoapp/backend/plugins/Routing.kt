package com.todoapp.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.http.*
import com.todoapp.backend.models.TodoItem
import com.todoapp.backend.models.TodoItemRequest
import com.todoapp.backend.repositories.TodoRepository

fun Application.configureRouting() {
    val todoRepository = TodoRepository()
    
    routing {
        route("/api/todos") {
            // Get all todos
            get {
                val todos = todoRepository.getAllTodos()
                call.respond(todos)
            }
            
            // Get a specific todo
            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                    return@get
                }
                
                val todo = todoRepository.getTodo(id)
                if (todo == null) {
                    call.respond(HttpStatusCode.NotFound, "Todo not found")
                } else {
                    call.respond(todo)
                }
            }
            
            // Create a new todo
            post {
                val todoRequest = call.receive<TodoItemRequest>()
                val todo = todoRepository.addTodo(todoRequest)
                call.respond(HttpStatusCode.Created, todo)
            }
            
            // Update a todo
            put("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                    return@put
                }
                
                val todoRequest = call.receive<TodoItemRequest>()
                val updated = todoRepository.updateTodo(id, todoRequest)
                
                if (updated) {
                    val updatedTodo = todoRepository.getTodo(id)
                    call.respond(updatedTodo!!)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Todo not found")
                }
            }
            
            // Delete a todo
            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                    return@delete
                }
                
                val deleted = todoRepository.deleteTodo(id)
                if (deleted) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Todo not found")
                }
            }
        }
    }
}