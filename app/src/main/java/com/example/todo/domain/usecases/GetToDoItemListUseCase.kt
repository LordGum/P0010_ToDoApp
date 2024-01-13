package com.example.todo.domain.usecases

import com.example.todo.domain.Repository

class GetToDoItemListUseCase(
    private val repository: Repository
) {
    operator fun invoke() = repository.getToDoItemList()
}