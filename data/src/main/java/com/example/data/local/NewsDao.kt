package com.example.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.example.data.entities.Article

@Dao
interface NewsDao {
    @Query("SELECT * FROM Article")
    fun pagingSource(): PagingSource<Int, Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: List<Article>)
}