package com.example.todo.domain.entities

data class ToDoItem (
    var id: Int = UNDEFINED_ID,
    var done: Boolean,
    var priority: Int,
    val dataCreation: String,
    var name: String,
    var description: String = ""
){
    companion object{
        const val UNDEFINED_ID = 0
    }
}