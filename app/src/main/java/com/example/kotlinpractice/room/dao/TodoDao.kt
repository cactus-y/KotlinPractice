package com.example.kotlinpractice.room.dao

import androidx.room.*
import com.example.kotlinpractice.room.entity.TodoItem

@Dao
interface TodoDao {
    @Insert
    fun insertTodo(item : TodoItem)

    @Delete
    fun deleteTodo(item: TodoItem)

    @Update
    fun updateTodo(item: TodoItem)

    @Query("SELECT * FROM todo")
    fun getAllTodo() : List<TodoItem>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodo(id : Int) : TodoItem
}