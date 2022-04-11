package com.example.myapplication.data.api

import com.example.myapplication.API_KEY
import com.example.myapplication.data.dataObjects.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): News
}