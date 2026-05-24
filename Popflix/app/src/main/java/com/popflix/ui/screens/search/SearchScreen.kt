package com.popflix.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.popflix.domain.model.Content
import com.popflix.ui.components.BottomNavigationBar
import com.popflix.ui.components.ContentCard

@Composable
fun SearchScreen(
    onNavigateToDetails: (Int, String) -> Unit,
    onNavigateToPlayer: (Int, String, String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedTab by remember { mutableStateOf(0) }
    val focusRequester = remember { FocusRequester() }

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
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { 
                    searchQuery = it
                    viewModel.search(it.text)
                },
                placeholder = { Text("Search movies & TV shows...") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    if (searchQuery.text.isNotEmpty()) {
                        IconButton(onClick = { 
                            searchQuery = TextFieldValue("")
                            viewModel.clearSearch()
                        }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequester),
                singleLine = true
            )

            // Results
            when (val state = uiState) {
                is SearchUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is SearchUiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 140.dp),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.results) { content ->
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
                is SearchUiState.Empty -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                is SearchUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = state.message)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.search(searchQuery.text) }) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}

// TODO: Implement SearchViewModel with actual TMDB search integration
sealed class SearchUiState {
    object Loading : SearchUiState()
    data class Success(val results: List<Content>) : SearchUiState()
    data class Empty(val message: String = "No results found") : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}

// Placeholder ViewModel - TODO: Implement with repository
class SearchViewModel @javax.inject.Inject constructor() : androidx.lifecycle.ViewModel() {
    private val _uiState = kotlinx.coroutines.flow.MutableStateFlow<SearchUiState>(SearchUiState.Empty("Search for movies and TV shows"))
    val uiState: kotlinx.coroutines.flow.StateFlow<SearchUiState> = _uiState

    fun search(query: String) {
        // TODO: Implement actual search via repository
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            // Simulate search delay
            kotlinx.coroutines.delay(500)
            _uiState.value = SearchUiState.Empty("No results found for \"$query\"")
        }
    }

    fun clearSearch() {
        _uiState.value = SearchUiState.Empty("Search for movies and TV shows")
    }
}
