package com.example.todo.domain.usecases

import com.example.todo.domain.Repository
import com.example.todo.domain.entities.ToDoItem

class AddToDoItemUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(item: ToDoItem) = repository.addToDoItem(item)
}