package com.todoapp.backend.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import com.todoapp.backend.db.tables.TodoItems

object DatabaseFactory {
    fun init() {
        // Connect to the database
        val database = Database.connect(hikari())
        
        // Create tables
        transaction(database) {
            SchemaUtils.create(TodoItems)
        }
    }
    
    private fun hikari(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = System.getenv("JDBC_DATABASE_URL") ?: "jdbc:postgresql://localhost:5432/todoapp"
            username = System.getenv("JDBC_DATABASE_USERNAME") ?: "postgres"
            password = System.getenv("JDBC_DATABASE_PASSWORD") ?: "pass"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }
    
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}