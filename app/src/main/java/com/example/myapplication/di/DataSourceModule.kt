package com.example.myapplication.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import com.example.myapplication.data.dataObjects.Article
import com.example.myapplication.data.remote.mediators.NewsRemoteMediator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class DataSourceModule {
    @OptIn(ExperimentalPagingApi::class)
    @Binds
    abstract fun bindNewsRemoteMediator(newsRemoteMediatorImpl: NewsRemoteMediator): RemoteMediator<Int, Article>
}