package com.example.myapplication.ui.screens.main

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.example.data.entities.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Immutable
data class MainUiState(val query: String = "apple")