package com.popflix.ui.screens.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.popflix.domain.model.Content
import com.popflix.ui.components.BottomNavigationBar
import com.popflix.ui.components.ContentCard

@Composable
fun WatchlistScreen(
    onNavigateToDetails: (Int, String) -> Unit,
    onNavigateToPlayer: (Int, String, String) -> Unit,
    viewModel: WatchlistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "My List",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )

            when (val state = uiState) {
                is WatchlistUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is WatchlistUiState.Success -> {
                    if (state.items.isEmpty()) {
                        EmptyWatchlist()
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 140.dp),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(state.items) { content ->
                                ContentCard(
                                    content = content,
                                    onClick = {
                                        val contentType = when (content) {
                                            is Content.MovieContent -> "movie"
                                            is Content.TvShowContent -> "tv"
                                        }
                                        onNavigateToDetails(content.id, contentType)
                                    }
                                )
                            }
                        }
                    }
                }
                is WatchlistUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = state.message)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.loadWatchlist() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyWatchlist() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.Bookmark,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your watchlist is empty",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Add movies and TV shows to watch later",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

sealed class WatchlistUiState {
    object Loading : WatchlistUiState()
    data class Success(val items: List<Content>) : WatchlistUiState()
    data class Error(val message: String) : WatchlistUiState()
}

// Placeholder ViewModel - TODO: Implement with Room DAO integration
class WatchlistViewModel @javax.inject.Inject constructor() : androidx.lifecycle.ViewModel() {
    private val _uiState = kotlinx.coroutines.flow.MutableStateFlow<WatchlistUiState>(WatchlistUiState.Loading)
    val uiState: kotlinx.coroutines.flow.StateFlow<WatchlistUiState> = _uiState

    init {
        loadWatchlist()
    }

    fun loadWatchlist() {
        // TODO: Load actual watchlist from WatchlistDao
        viewModelScope.launch {
            _uiState.value = WatchlistUiState.Loading
            kotlinx.coroutines.delay(500)
            // Empty list for now
            _uiState.value = WatchlistUiState.Success(emptyList())
        }
    }
}
