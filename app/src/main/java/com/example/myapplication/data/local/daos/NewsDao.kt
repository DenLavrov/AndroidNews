package com.example.myapplication.data.local.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.example.myapplication.data.dataObjects.Article

@Dao
interface NewsDao {
    @Query("SELECT * FROM Article")
    fun pagingSource(): PagingSource<Int, Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: List<Article>)
}