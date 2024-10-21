package com.example.myapplication.main.data

import kotlinx.serialization.json.Json

const val API_KEY = "93c777e00ec84c33ac5420b878c577c0"
const val BASE_URL = "https://newsapi.org/v2/"

val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}