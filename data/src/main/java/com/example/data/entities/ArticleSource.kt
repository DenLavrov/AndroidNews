package com.example.data.entities

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class ArticleSource(val name: String?)