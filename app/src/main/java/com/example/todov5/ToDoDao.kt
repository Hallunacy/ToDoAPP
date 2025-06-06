package com.example.todov5

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo_items ORDER BY id DESC")
    fun getAll(): LiveData<List<ToDoItemEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ToDoItemEntity)
    @Update
    suspend fun update(item: ToDoItemEntity)
    @Delete
    suspend fun delete(item: ToDoItemEntity)
    @Query("DELETE FROM todo_items WHERE id = :id")
    suspend fun deleteById(id: Int)
}
