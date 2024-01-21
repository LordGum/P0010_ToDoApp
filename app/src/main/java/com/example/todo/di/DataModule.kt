package com.example.todo.di

import android.app.Application
import com.example.todo.data.RepositoryImpl
import com.example.todo.data.database.AppDatabase
import com.example.todo.data.database.ToDoDao
import com.example.todo.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {
        @ApplicationScope
        @Provides
        fun provideToDoDao(
            application: Application
        ): ToDoDao {
            return  AppDatabase.getInstance(application).toDoDao()
        }
    }
}