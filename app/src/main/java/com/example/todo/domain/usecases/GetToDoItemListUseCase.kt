package com.example.todo.domain.usecases

import com.example.todo.domain.Repository
import javax.inject.Inject

class GetToDoItemListUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.getToDoItemList()
}