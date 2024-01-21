package com.example.todo.presentation.main_activity

import androidx.lifecycle.ViewModel
import com.example.todo.domain.entities.ToDoItem
import com.example.todo.domain.usecases.DeleteToDoItemUseCase
import com.example.todo.domain.usecases.GetToDoItemListUseCase
import com.example.todo.domain.usecases.RefactorToDoItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getToDoItemListUseCase: GetToDoItemListUseCase,
    private val deleteToDoItemUseCase: DeleteToDoItemUseCase,
    private val refactorToDoItemUseCase: RefactorToDoItemUseCase
): ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    val itemList = getToDoItemListUseCase()

    fun deleteToDoItem(id: Int) {
        scope.launch {
            deleteToDoItemUseCase(id)
        }
    }

    fun changeEnableState(item: ToDoItem) {
        scope.launch {
            val newItem = item.copy(done = !item.done)
             refactorToDoItemUseCase(newItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}