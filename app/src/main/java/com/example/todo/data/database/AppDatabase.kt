package com.example.todo.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.data.database.model.ToDoItemDbModel

@Database(entities = [ToDoItemDbModel::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    companion object{
        private var db: AppDatabase? = null
        private const val DB_NAME = "toDoMain.db"
        private val LOCK = Any()

        fun getInstance(application: Application): AppDatabase {
            db?.let { return it }
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME,
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                db = instance
                return instance
            }
        }
    }
    abstract fun toDoDao(): ToDoDao
}