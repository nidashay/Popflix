package com.popflix.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.popflix.domain.model.Cast

@Composable
fun DetailsScreen(
    contentId: Int,
    contentType: String,
    onNavigateBack: () -> Unit,
    onNavigateToPlayer: (Int, String, String) -> Unit,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var isWatchlisted by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is DetailsUiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is DetailsUiState.Success -> {
                    val content = state.content
                    
                    // Backdrop image with gradient overlay
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(300.dp)
                        ) {
                            if (content.backdropPath != null) {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/original${content.backdropPath}",
                                    contentDescription = content.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            
                            // Gradient overlay
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                MaterialTheme.colorScheme.background
                                            )
                                        )
                                    )
                            )
                            
                            // Back button
                            IconButton(
                                onClick = onNavigateBack,
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(16.dp)
                                    .background(
                                        Color.Black.copy(alpha = 0.5f),
                                        CircleShape
                                    )
                            ) {
                                Icon(
                                    Icons.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                    
                    // Title and metadata
                    item {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = content.title,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = content.releaseDate.take(4),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                
                                Spacer(modifier = Modifier.width(16.dp))
                                
                                Text(
                                    text = "★ ${state.content.voteAverage}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                
                                Spacer(modifier = Modifier.width(16.dp))
                                
                                Text(
                                    text = contentType.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant,
                                            MaterialTheme.shapes.small
                                        )
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Action buttons
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = {
                                        // TODO: Get video URL from VideoSourceManager
                                        val fakeVideoUrl = "https://example.com/video.mp4"
                                        onNavigateToPlayer(contentId, contentType, fakeVideoUrl)
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        Icons.Filled.PlayArrow,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Play")
                                }
                                
                                OutlinedIconButton(
                                    onClick = { isWatchlisted = !isWatchlisted },
                                    modifier = Modifier.size(56.dp)
                                ) {
                                    Icon(
                                        if (isWatchlisted) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                                        contentDescription = if (isWatchlisted) "Remove from watchlist" else "Add to watchlist"
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            // Overview
                            Text(
                                text = "Overview",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = content.overview,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5
                            )
                        }
                    }
                    
                    // Cast section (if available)
                    if (state.cast.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "Cast",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                        
                        item {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(state.cast) { cast ->
                                    CastCard(cast = cast)
                                }
                            }
                        }
                    }
                }
                is DetailsUiState.Error -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = state.message)
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(onClick = { viewModel.loadDetails(contentId, contentType) }) {
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
private fun CastCard(cast: Cast) {
    Column(
        modifier = Modifier.width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            if (cast.profilePath != null) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w200${cast.profilePath}",
                    contentDescription = cast.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = cast.name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Text(
            text = cast.character,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

sealed class DetailsUiState {
    object Loading : DetailsUiState()
    data class Success(
        val content: com.popflix.domain.model.Content,
        val cast: List<Cast> = emptyList()
    ) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
}

// Placeholder ViewModel - TODO: Implement with repository
class DetailsViewModel @javax.inject.Inject constructor() : androidx.lifecycle.ViewModel() {
    private val _uiState = kotlinx.coroutines.flow.MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: kotlinx.coroutines.flow.StateFlow<DetailsUiState> = _uiState

    fun loadDetails(contentId: Int, contentType: String) {
        // TODO: Load actual details from TMDB API via repository
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            kotlinx.coroutines.delay(500)
            
            // Placeholder data
            _uiState.value = DetailsUiState.Success(
                content = if (contentType == "movie") {
                    com.popflix.domain.model.Content.MovieContent(
                        com.popflix.domain.model.Movie(
                            id = contentId,
                            title = "Sample Movie",
                            overview = "This is a sample movie overview. In a real app, this would be fetched from the TMDB API.",
                            posterPath = null,
                            backdropPath = null,
                            releaseDate = "2024-01-01",
                            voteAverage = 7.5,
                            voteCount = 100,
                            genreIds = listOf(1, 2),
                            runtime = 120,
                            originalLanguage = "en",
                            popularity = 50.0
                        )
                    )
                } else {
                    com.popflix.domain.model.Content.TvShowContent(
                        com.popflix.domain.model.TvShow(
                            id = contentId,
                            name = "Sample TV Show",
                            overview = "This is a sample TV show overview. In a real app, this would be fetched from the TMDB API.",
                            posterPath = null,
                            backdropPath = null,
                            firstAirDate = "2024-01-01",
                            voteAverage = 8.0,
                            voteCount = 200,
                            genreIds = listOf(1, 3),
                            episodeRunTime = listOf(45),
                            originalLanguage = "en",
                            popularity = 75.0
                        )
                    )
                },
                cast = listOf(
                    Cast(1, "Actor One", "Main Character", null),
                    Cast(2, "Actor Two", "Supporting Role", null),
                    Cast(3, "Actor Three", "Villain", null)
                )
            )
        }
    }
}
