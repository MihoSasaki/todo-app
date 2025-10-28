package com.todoapp.backend.repositories

import com.todoapp.backend.db.DatabaseFactory.dbQuery
import com.todoapp.backend.db.tables.TodoItems
import com.todoapp.backend.models.TodoItem
import com.todoapp.backend.models.TodoItemRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime

class TodoRepository {

    suspend fun getAllTodos(): List<TodoItem> = dbQuery {
        TodoItems.selectAll()
            .map { it.toTodoItem() }
    }

    suspend fun getTodo(id: Int): TodoItem? = dbQuery {
        TodoItems.select { TodoItems.id eq id }
            .map { it.toTodoItem() }
            .singleOrNull()
    }

    suspend fun addTodo(todoRequest: TodoItemRequest): TodoItem = dbQuery {
        val insertStatement = TodoItems.insert {
            it[title] = todoRequest.title
            it[description] = todoRequest.description
            it[completed] = todoRequest.completed
            it[createdAt] = LocalDateTime.now()
            it[updatedAt] = LocalDateTime.now()
        }

        insertStatement.resultedValues?.single()?.toTodoItem()
            ?: throw IllegalStateException("Insert failed")
    }

    suspend fun updateTodo(id: Int, todoRequest: TodoItemRequest): Boolean = dbQuery {
        TodoItems.update({ TodoItems.id eq id }) {
            it[title] = todoRequest.title
            it[description] = todoRequest.description
            it[completed] = todoRequest.completed
            it[updatedAt] = LocalDateTime.now()
        } > 0
    }

    suspend fun deleteTodo(id: Int): Boolean = dbQuery {
        TodoItems.deleteWhere { TodoItems.id eq id } > 0
    }

    private fun ResultRow.toTodoItem(): TodoItem = TodoItem(
        id = this[TodoItems.id],
        title = this[TodoItems.title],
        description = this[TodoItems.description],
        completed = this[TodoItems.completed],
        createdAt = this[TodoItems.createdAt].toString(),
        updatedAt = this[TodoItems.updatedAt].toString()
    )
}
