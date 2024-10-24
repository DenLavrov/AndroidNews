package com.example.myapplication.main.data.model

import androidx.room.Embedded
import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class News(
    val status: String,
    val totalResults: Int,
    @Embedded val articles: List<Article>
)