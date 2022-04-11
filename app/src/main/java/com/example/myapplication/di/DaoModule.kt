package com.example.myapplication.di

import com.example.myapplication.data.local.databases.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DaoModule{
    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase) = newsDatabase.newsDao()
}