package com.example.todo.domain.usecases

import com.example.todo.domain.Repository
import javax.inject.Inject

class GetToDoItemUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int) = repository.getToDoItem(id)
}