package com.example.data.repositories

import androidx.paging.*
import com.example.data.local.NewsDao
import com.example.data.entities.Article
import com.example.data.remote.NewsRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NewsRepository {
    fun getNews(initialCount: Int, pageSize: Int, query: String) : Flow<PagingData<Article>>

    fun updateQuery(query: String)
}

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val newsRemoteMediator: NewsRemoteMediator
): NewsRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getNews(
        initialCount: Int,
        pageSize: Int,
        query: String
    ) = Pager(
        PagingConfig(pageSize, initialLoadSize = initialCount, enablePlaceholders = false),
        remoteMediator = newsRemoteMediator.apply { this.query = query }
    ) {
        newsDao.pagingSource()
    }.flow

    override fun updateQuery(query: String) {
        newsRemoteMediator.query = query
    }
}