package com.popflix.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
fun HomeScreen(
    onNavigateToDetails: (Int, String) -> Unit,
    onNavigateToPlayer: (Int, String, String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                // Header
                Text(
                    text = "Popflix",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is HomeUiState.Success -> {
                    // Trending Movies Row
                    if (state.trendingMovies.isNotEmpty()) {
                        item {
                            SectionHeader(title = "Trending Movies")
                        }
                        item {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(state.trendingMovies) { movie ->
                                    ContentCard(
                                        content = Content.MovieContent(movie),
                                        onClick = {
                                            onNavigateToDetails(movie.id, "movie")
                                        }
                                    )
                                }
                            }
                        }
                    }
                    
                    // Trending TV Shows Row
                    if (state.trendingTvShows.isNotEmpty()) {
                        item {
                            SectionHeader(title = "Trending TV Shows")
                        }
                        item {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(state.trendingTvShows) { tvShow ->
                                    ContentCard(
                                        content = Content.TvShowContent(tvShow),
                                        onClick = {
                                            onNavigateToDetails(tvShow.id, "tv")
                                        }
                                    )
                                }
                            }
                        }
                    }
                    
                    // Popular Movies Row
                    if (state.popularMovies.isNotEmpty()) {
                        item {
                            SectionHeader(title = "Popular Movies")
                        }
                        item {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(state.popularMovies) { movie ->
                                    ContentCard(
                                        content = Content.MovieContent(movie),
                                        onClick = {
                                            onNavigateToDetails(movie.id, "movie")
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                is HomeUiState.Error -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = state.message)
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(onClick = { viewModel.loadContent() }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}
