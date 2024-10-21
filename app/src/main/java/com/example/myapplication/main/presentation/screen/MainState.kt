package com.example.myapplication.main.presentation.screen

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.example.myapplication.main.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class MainState(
    val query: String = "apple",
    val isLoading: Boolean = true,
    val data: Flow<PagingData<Article>> = emptyFlow()
)