package com.example.todo.domain.usecases

import com.example.todo.domain.Repository
import com.example.todo.domain.entities.ToDoItem
import javax.inject.Inject

class AddToDoItemUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(item: ToDoItem) = repository.addToDoItem(item)
}