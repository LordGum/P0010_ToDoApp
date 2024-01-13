package com.example.todo.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.todo.data.database.AppDatabase
import com.example.todo.data.mappers.Mapper
import com.example.todo.domain.Repository
import com.example.todo.domain.entities.ToDoItem

class RepositoryImpl(
    application: Application
): Repository {

    private val toDoDao = AppDatabase.getInstance(application).toDoDao()
    private val mapper = Mapper()

    override suspend fun addToDoItem(item: ToDoItem) {
        toDoDao.addToDoItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun deleteToDoItem(id: Int) {
        toDoDao.deleteToDoItem(id)
    }

    override fun getToDoItemList(): LiveData<List<ToDoItem>> {
        return toDoDao.getToDoItemList().map {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override suspend fun getToDoItem(id: Int): ToDoItem {
        return mapper.mapDbModelToEntity(toDoDao.getToDoItem(id))
    }

    override suspend fun refactorToDoItem(item: ToDoItem) {
        toDoDao.addToDoItem(mapper.mapEntityToDbModel(item))
    }
}