package com.example.todo.presentation

import android.app.Application
import com.example.todo.di.DaggerApplicationComponent

class ToDoApplication: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}