package com.example.todo.presentation.add_item_package

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todo.data.RepositoryImpl
import com.example.todo.domain.entities.ToDoItem
import com.example.todo.domain.usecases.AddToDoItemUseCase
import com.example.todo.domain.usecases.GetToDoItemUseCase
import com.example.todo.domain.usecases.RefactorToDoItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddItemViewModel(application: Application): AndroidViewModel(application) {
    private val repository = RepositoryImpl(application)

    private val addToDoItemUseCase = AddToDoItemUseCase(repository)
    private val getToDoItemUseCase = GetToDoItemUseCase(repository)
    private val refactorToDoItemUseCase = RefactorToDoItemUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _item = MutableLiveData<ToDoItem>()
    val item: LiveData<ToDoItem>
        get() = _item

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getToDoItem(itemId: Int) {
        scope.launch {
            val currentItem = getToDoItemUseCase(itemId)
            _item.postValue(currentItem)
        }
    }

    private fun finishWork() {
        _shouldCloseScreen.postValue(Unit)
    }

    fun addToDoItem(inputName: String?, inputDescription: String?, inputDate: String?, inputPriority: Int) {
        val name = parseName(inputName)
        val description = parseDescription(inputDescription)
        val date = parseDate(inputDate)
        val fieldsValid = validateInput(name, description, date)
        if (fieldsValid) {
            scope.launch {
                val item = ToDoItem(
                    name = name,
                    done = false,
                    description = description,
                    dataCreation = date,
                    priority = inputPriority
                )
                addToDoItemUseCase(item)
                finishWork()
            }
        }
    }

    fun editToDoItem(inputName: String?, inputDescription: String?, inputDate: String?, inputPriority: Int) {
        val name = parseName(inputName)
        val description = parseDescription(inputDescription)
        val date = parseDate(inputDate)
        val fieldsValid = validateInput(name, description, date)
        if (fieldsValid) {
            _item.value?.let {
                scope.launch {
                    val item = it.copy(
                        name = name,
                        description = description,
                        dataCreation = date,
                        priority = inputPriority
                    )
                    refactorToDoItemUseCase(item)
                    finishWork()
                }
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }
    private fun parseDescription(inputDesc: String?): String {
        return inputDesc?.trim() ?: ""
    }
    private fun parseDate(inputDate: String?): String {
        return inputDate?.trim() ?: "00.00.00"
    }
    private fun validateInput(name: String, description: String, date: String): Boolean {
        if (name.isBlank()) return false
        if (description.isBlank()) return false
        //TODO(stranger valid for date)
        if (date == "00.00.00") return false
        else return true
    }
}