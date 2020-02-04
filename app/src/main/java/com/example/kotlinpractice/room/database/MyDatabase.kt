package com.example.kotlinpractice.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlinpractice.room.dao.TodoDao
import com.example.kotlinpractice.room.entity.TodoItem

@Database(entities = [TodoItem::class], version = 1)
abstract class MyDatabase : RoomDatabase(){
    abstract fun todoDao() : TodoDao

    companion object{
        private var myDatabase: MyDatabase? = null

        fun getInstance(context : Context): MyDatabase? {
            if(myDatabase == null){
                myDatabase = Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "MyDatabase.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return myDatabase
        }
    }
}