package com.example.todo.domain.usecases

import com.example.todo.domain.Repository

class DeleteToDoItemUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int) = repository.deleteToDoItem(id)
}