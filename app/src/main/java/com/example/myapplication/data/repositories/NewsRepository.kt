package com.example.myapplication.data.repositories

import androidx.paging.*
import com.example.myapplication.data.local.daos.NewsDao
import com.example.myapplication.data.dataObjects.Article
import com.example.myapplication.data.remote.mediators.NewsRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NewsRepository {
    fun getNews(initialCount: Int, pageSize: Int, getQuery: () -> String) : Flow<PagingData<Article>>
}

class NewsRepositoryImpl @OptIn(ExperimentalPagingApi::class)
@Inject constructor(private val newsDao: NewsDao,
                    private val newsRemoteMediator: NewsRemoteMediator
): NewsRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getNews(
        initialCount: Int,
        pageSize: Int,
        getQuery: () -> String
    ) = Pager(
        PagingConfig(pageSize, initialLoadSize = initialCount, enablePlaceholders = false),
        remoteMediator = newsRemoteMediator.apply { this.getQuery = getQuery }
    ) {
        newsDao.pagingSource()
    }.flow
}