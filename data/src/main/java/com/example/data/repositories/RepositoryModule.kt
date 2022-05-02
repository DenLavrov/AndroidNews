package com.example.data.repositories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository

    @Binds
    @ViewModelScoped
    abstract fun bindPlatformRepository(platformRepositoryImpl: PlatformRepositoryImpl): PlatformRepository
}