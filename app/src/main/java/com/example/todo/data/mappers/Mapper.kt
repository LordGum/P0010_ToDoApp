package com.example.todo.data.mappers

import com.example.todo.data.database.model.ToDoItemDbModel
import com.example.todo.domain.entities.ToDoItem

class Mapper {
    fun mapDbModelToEntity(item: ToDoItemDbModel) = ToDoItem(
        item.id,
        item.done,
        item.priority,
        item.dataCreation,
        item.name,
        item.description
    )

    fun mapEntityToDbModel(item: ToDoItem) = ToDoItemDbModel(
        item.id,
        item.done,
        item.priority,
        item.dataCreation,
        item.name,
        item.description
    )
}