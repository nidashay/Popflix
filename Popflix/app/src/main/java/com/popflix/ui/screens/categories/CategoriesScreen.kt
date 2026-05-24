package com.popflix.ui.screens.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.popflix.domain.model.Genre
import com.popflix.ui.components.BottomNavigationBar

@Composable
fun CategoriesScreen(
    onNavigateToDetails: (Int, String) -> Unit,
    viewModel: CategoriesViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

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
                text = "Categories",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )

            // Category filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { selectedCategory = null },
                    label = { Text("All") }
                )
                FilterChip(
                    selected = selectedCategory == "Movies",
                    onClick = { selectedCategory = "Movies" },
                    label = { Text("Movies") }
                )
                FilterChip(
                    selected = selectedCategory == "TV Shows",
                    onClick = { selectedCategory = "TV Shows" },
                    label = { Text("TV Shows") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (val state = uiState) {
                is CategoriesUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is CategoriesUiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 150.dp),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.genres) { genre ->
                            GenreCard(
                                genre = genre,
                                onClick = {
                                    // TODO: Navigate to genre-specific content
                                }
                            )
                        }
                    }
                }
                is CategoriesUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = state.message)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.loadGenres() }) {
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
private fun GenreCard(
    genre: Genre,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1.5f)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Category,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = genre.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

sealed class CategoriesUiState {
    object Loading : CategoriesUiState()
    data class Success(val genres: List<Genre>) : CategoriesUiState()
    data class Error(val message: String) : CategoriesUiState()
}

// Placeholder ViewModel - TODO: Implement with repository
class CategoriesViewModel @javax.inject.Inject constructor() : androidx.lifecycle.ViewModel() {
    private val _uiState = kotlinx.coroutines.flow.MutableStateFlow<CategoriesUiState>(CategoriesUiState.Loading)
    val uiState: kotlinx.coroutines.flow.StateFlow<CategoriesUiState> = _uiState

    init {
        loadGenres()
    }

    fun loadGenres() {
        // TODO: Load actual genres from TMDB API via repository
        viewModelScope.launch {
            _uiState.value = CategoriesUiState.Loading
            // Placeholder genres
            kotlinx.coroutines.delay(500)
            _uiState.value = CategoriesUiState.Success(
                listOf(
                    Genre(1, "Action"),
                    Genre(2, "Comedy"),
                    Genre(3, "Drama"),
                    Genre(4, "Horror"),
                    Genre(5, "Sci-Fi"),
                    Genre(6, "Thriller"),
                    Genre(7, "Romance"),
                    Genre(8, "Documentary"),
                    Genre(9, "Animation"),
                    Genre(10, "Crime")
                )
            )
        }
    }
}
