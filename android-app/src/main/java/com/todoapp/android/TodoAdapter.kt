package com.todoapp.android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.todoapp.android.databinding.ItemTodoBinding
import com.todoapp.android.models.TodoItem

class TodoAdapter(
    private val onItemClick: (TodoItem) -> Unit,
    private val onCompleteClick: (TodoItem) -> Unit,
    private val onDeleteClick: (TodoItem) -> Unit
) : ListAdapter<TodoItem, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }

    inner class TodoViewHolder(private val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
            
            binding.buttonComplete.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onCompleteClick(getItem(position))
                }
            }
            
            binding.buttonDelete.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClick(getItem(position))
                }
            }
        }
        
        fun bind(todo: TodoItem) {
            binding.textViewTitle.text = todo.title
            binding.textViewDescription.text = todo.description ?: ""
            
            // Update complete button text based on todo status
            binding.buttonComplete.text = if (todo.completed) {
                binding.root.context.getString(R.string.mark_incomplete)
            } else {
                binding.root.context.getString(R.string.mark_complete)
            }
            
            // Apply visual indication of completed status
            binding.textViewTitle.alpha = if (todo.completed) 0.5f else 1.0f
            binding.textViewDescription.alpha = if (todo.completed) 0.5f else 1.0f
        }
    }
}

class TodoDiffCallback : DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem == newItem
    }
}