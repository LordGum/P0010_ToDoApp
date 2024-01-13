package com.example.todo.presentation.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.todo.data.RepositoryImpl
import com.example.todo.domain.entities.ToDoItem
import com.example.todo.domain.usecases.AddToDoItemUseCase
import com.example.todo.domain.usecases.DeleteToDoItemUseCase
import com.example.todo.domain.usecases.GetToDoItemListUseCase
import com.example.todo.domain.usecases.RefactorToDoItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository = RepositoryImpl(application)

    private val getToDoItemListUseCase = GetToDoItemListUseCase(repository)
    private val deleteToDoItemUseCase = DeleteToDoItemUseCase(repository)
    private val refactorToDoItemUseCase = RefactorToDoItemUseCase(repository)

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