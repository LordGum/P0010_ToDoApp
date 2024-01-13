package com.example.todo.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo.data.database.model.ToDoItemDbModel

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDoItem(item: ToDoItemDbModel)

    @Query("DELETE FROM to_do_list WHERE id=:itemId")
    suspend fun deleteToDoItem(itemId: Int)

    @Query("SELECT * FROM to_do_list ORDER BY id")
    fun getToDoItemList(): LiveData<List<ToDoItemDbModel>>

    @Query("SELECT * FROM to_do_list WHERE id=:itemId")
    suspend fun getToDoItem(itemId: Int): ToDoItemDbModel
}