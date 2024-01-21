package com.example.todo.domain.usecases

import com.example.todo.domain.Repository
import javax.inject.Inject

class DeleteToDoItemUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int) = repository.deleteToDoItem(id)
}