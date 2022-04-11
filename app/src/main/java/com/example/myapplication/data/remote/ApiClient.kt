package com.example.myapplication.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@ExperimentalSerializationApi
class ApiClient(serverUrl: String) {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    private val okClient = OkHttpClient.Builder()
        .callTimeout(1, TimeUnit.MINUTES)
        .retryOnConnectionFailure(true)
        .build()
    private val adapterBuilder = Retrofit.Builder()
        .client(okClient)
        .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
        .baseUrl(serverUrl)
        .build()

    fun <S> createService(serviceClass: Class<S>): S = adapterBuilder.create(serviceClass)
}