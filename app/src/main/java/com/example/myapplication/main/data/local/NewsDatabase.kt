package com.example.myapplication.main.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.main.data.model.Article

@Database(entities = [Article::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}