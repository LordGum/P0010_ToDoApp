package com.example.todo.domain

import androidx.lifecycle.LiveData
import com.example.todo.domain.entities.ToDoItem

interface Repository {
    suspend fun addToDoItem(item: ToDoItem)
    suspend fun deleteToDoItem(id: Int)
    fun getToDoItemList(): LiveData<List<ToDoItem>>
    suspend fun getToDoItem(id: Int): ToDoItem
    suspend fun refactorToDoItem(item: ToDoItem)
}