package com.example.myapplication.data.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.local.daos.NewsDao
import com.example.myapplication.data.dataObjects.Article

@Database(entities = [Article::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}