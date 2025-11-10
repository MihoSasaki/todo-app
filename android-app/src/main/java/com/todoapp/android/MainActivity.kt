package com.todoapp.android

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.todoapp.android.databinding.ActivityMainBinding
import com.todoapp.android.models.TodoItem
import com.todoapp.android.models.TodoItemRequest
import com.todoapp.android.network.TodoApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoApiService: TodoApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupApiService()
        setupClickListeners()

        loadTodos()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter(
            onItemClick = { todo ->
                // Show edit dialog or navigate to detail screen
                Toast.makeText(this, "Clicked on ${todo.title}", Toast.LENGTH_SHORT).show()
            },
            onCompleteClick = { todo ->
                updateTodoStatus(todo)
            },
            onDeleteClick = { todo ->
                deleteTodo(todo)
            }
        )

        binding.recyclerViewTodos.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }
    }

    private fun setupApiService() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Android emulator address for localhost
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        todoApiService = retrofit.create(TodoApiService::class.java)
    }

    private fun setupClickListeners() {
        binding.fabAddTodo.setOnClickListener {
            // Show add todo dialog
            showAddTodoDialog()
        }
    }

    private fun loadTodos() {
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val todos = todoApiService.getAllTodos()
                todoAdapter.submitList(todos)
                binding.progressBar.visibility = View.GONE

                // Show empty state if no todos
                if (todos.isEmpty()) {
                    binding.textViewEmpty.visibility = View.VISIBLE
                } else {
                    binding.textViewEmpty.visibility = View.GONE
                }
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Error loading todos: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateTodoStatus(todo: TodoItem) {
        lifecycleScope.launch {
            try {
                val todoRequest = TodoItemRequest(
                    title = todo.title,
                    description = todo.description,
                    completed = !todo.completed
                )
                todoApiService.updateTodo(todo.id, todoRequest)
                loadTodos() // Reload the list
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error updating todo: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteTodo(todo: TodoItem) {
        lifecycleScope.launch {
            try {
                val response = todoApiService.deleteTodo(todo.id)
                if (response.isSuccessful) {
                    loadTodos() // Reload the list
                } else {
                    Toast.makeText(this@MainActivity, "Error deleting todo: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.d("test", e.message.toString())
                Toast.makeText(this@MainActivity, "Error deleting todo: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showAddTodoDialog() {
        // Implementation of dialog to add a new todo
        // This would typically use a DialogFragment or AlertDialog
        // For simplicity, we'll just create a new todo with hardcoded values
        val newTodoRequest = TodoItemRequest(
            title = "New Todo",
            description = "This is a new todo item",
            completed = false
        )

        addTodo(newTodoRequest)
    }

    private fun addTodo(todoRequest: TodoItemRequest) {
        lifecycleScope.launch {
            try {
                todoApiService.createTodo(todoRequest)
                loadTodos() // Reload the list
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error adding todo: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
