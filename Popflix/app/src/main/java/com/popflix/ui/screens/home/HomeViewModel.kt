package com.popflix.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popflix.domain.model.Movie
import com.popflix.domain.model.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val trendingMovies: List<Movie> = emptyList(),
        val trendingTvShows: List<TvShow> = emptyList(),
        val popularMovies: List<Movie> = emptyList()
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState
    
    init {
        loadContent()
    }
    
    fun loadContent() {
        viewModelScope.launch {
            try {
                _uiState.value = HomeUiState.Loading
                // TODO: Implement actual data fetching from repository
                // For now, show empty success state
                _uiState.value = HomeUiState.Success()
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
