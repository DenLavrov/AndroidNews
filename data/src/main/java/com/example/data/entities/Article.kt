package com.example.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Entity
data class Article(@Transient @PrimaryKey var id: Int = 0,
                   @Embedded val source: ArticleSource? = null,
                   val author: String? = null,
                   val title: String,
                   val description: String,
                   val url: String,
                   val urlToImage: String? = null,
                   val publishedAt: String? = null,
                   val content: String)
