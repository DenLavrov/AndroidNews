package com.example.myapplication.main.data.repository

import androidx.paging.*
import com.example.myapplication.main.data.local.NewsDao
import com.example.myapplication.main.data.model.Article
import com.example.myapplication.main.data.remote.NewsRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NewsRepository {
    val query: String

    fun getNews(initialCount: Int = 10, pageSize: Int = 5, query: String): Flow<PagingData<Article>>

    fun updateQuery(query: String)
}

class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val newsRemoteMediator: NewsRemoteMediator
) : NewsRepository {
    override val query: String
        get() = newsRemoteMediator.query

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