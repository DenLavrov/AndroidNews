package com.example.myapplication.main.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.myapplication.main.data.network.NewsApi
import com.example.myapplication.main.data.local.NewsDao
import com.example.myapplication.main.data.model.Article
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
    private val newsDao: NewsDao,
    private val newsApi: NewsApi
) : RemoteMediator<Int, Article>() {
    lateinit var query: String
    private var id = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    id = 0
                    1
                }

                LoadType.PREPEND -> return MediatorResult.Success(false)
                LoadType.APPEND -> state.pages.size + 1
            }
            val news = newsApi.getNews(
                loadKey,
                if (loadKey == 1) state.config.initialLoadSize
                else state.config.pageSize,
                query
            )
            newsDao.insert(news.articles.onEach { it.id = id++ })
            MediatorResult.Success(false)
        } catch (t: Throwable) {
            MediatorResult.Error(t)
        }
    }
}