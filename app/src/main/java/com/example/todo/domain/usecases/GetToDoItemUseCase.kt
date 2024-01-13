package com.example.todo.domain.usecases

import com.example.todo.domain.Repository

class GetToDoItemUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int) = repository.getToDoItem(id)
}