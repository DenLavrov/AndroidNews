package com.example.myapplication.di

import com.example.myapplication.BASE_URL
import com.example.myapplication.data.api.NewsApi
import com.example.myapplication.data.remote.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    @Suppress("OPT_IN_USAGE")
    @Provides
    @Singleton
    fun provideNewsApiService(): NewsApi {
        val apiClient = ApiClient(BASE_URL)
        return apiClient.createService(NewsApi::class.java)
    }
}