package com.example.myapplication.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.useCases.GetNewsUseCase
import com.example.domain.useCases.OpenUrlUseCase
import com.example.domain.useCases.UpdateQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getNewsUseCaseUseCase: GetNewsUseCase,
    updateQueryUseCase: UpdateQueryUseCase,
    private val openUrlUseCase: OpenUrlUseCase
): ViewModel(), MainUiActions {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: Flow<MainUiState> = _uiState

    val news = getNewsUseCaseUseCase(_uiState.value.query).cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            uiState.collect {
                updateQueryUseCase(it.query)
            }
        }
    }

    override fun updateQuery(query: String) = _uiState.update { it.copy(query = query) }

    override fun openUrl(url: String) = openUrlUseCase(url)
}