package com.example.myapplication.main.data.model

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class ArticleSource(val name: String?)