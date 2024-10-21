package com.example.myapplication.main.presentation.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.myapplication.main.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private companion object {
        const val STATE_KEY = "STATE_KEY"
    }

    private val _effects = MutableSharedFlow<Any>()
    val effects = _effects.asSharedFlow()

    private val _state =
        MutableStateFlow(MainState(query = savedStateHandle[STATE_KEY] ?: "apple"))
    val state = _state
        .onEach { savedStateHandle[STATE_KEY] = it.query }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MainState())

    private val newsFlow =
        newsRepository.getNews(query = _state.value.query).cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            state.debounce(1000).filter {
                it.query != newsRepository.query
            }.collect {
                newsRepository.updateQuery(it.query)
                effect(MainEffect.Refresh)
            }
        }
    }

    fun dispatch(action: MainAction) {
        viewModelScope.launch {
            _state.update { reduce(it, action) }
        }
    }

    private fun reduce(prevState: MainState, action: MainAction): MainState {
        return when (action) {
            MainAction.Init -> prevState.copy(data = newsFlow)
            is MainAction.UpdateQuery -> prevState.copy(query = action.query)
        }
    }

    private suspend fun effect(effect: Any) {
        _effects.emit(effect)
    }
}