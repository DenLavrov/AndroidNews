package com.example.myapplication.data.dataObjects

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class ArticleSource(val name: String?)