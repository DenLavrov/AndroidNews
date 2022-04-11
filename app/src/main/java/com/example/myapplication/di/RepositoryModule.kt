package com.example.myapplication.di

import com.example.myapplication.data.repositories.PlatformRepository
import com.example.myapplication.data.repositories.PlatformRepositoryImpl
import com.example.myapplication.data.repositories.NewsRepository
import com.example.myapplication.data.repositories.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository

    @Binds
    abstract fun bindPlatformRepository(platformRepositoryImpl: PlatformRepositoryImpl): PlatformRepository
}