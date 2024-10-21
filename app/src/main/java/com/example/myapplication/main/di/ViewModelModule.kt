package com.example.myapplication.main.di

import com.example.myapplication.main.data.repository.NewsRepository
import com.example.myapplication.main.data.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface ViewModelModule {

    @Binds
    @ViewModelScoped
    fun bindNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository
}