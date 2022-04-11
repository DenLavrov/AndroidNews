package com.example.myapplication.bl.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.myapplication.data.repositories.NewsRepository
import com.example.myapplication.data.repositories.PlatformRepository
import com.example.myapplication.ui.screens.MainScreenActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    newsRepository: NewsRepository,
    private val platformRepository: PlatformRepository):
    ViewModel(), MainScreenActions {
    private val mutableQuery = MutableStateFlow("apple")
    val query: Flow<String> = mutableQuery

    val news = newsRepository.getNews(50, 10) {
        mutableQuery.value
    }.cachedIn(viewModelScope)

    override fun updateQuery(query: String) = mutableQuery.update { query }

    override fun openUrl(url: String) = platformRepository.openUrl(url)
}