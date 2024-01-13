package com.example.todo.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to_do_list")
data class ToDoItemDbModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var done: Boolean,
    var priority: Int,
    val dataCreation: String,
    var name: String,
    var description: String = ""
)