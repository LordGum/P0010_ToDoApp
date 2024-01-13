package com.example.todo.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.todo.domain.entities.ToDoItem

class ToDoDiffCallback: DiffUtil.ItemCallback<ToDoItem>() {
    override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem == newItem
    }

}