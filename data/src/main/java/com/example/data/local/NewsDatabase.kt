package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.NewsDao
import com.example.data.entities.Article

@Database(entities = [Article::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}