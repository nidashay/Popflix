package com.popflix.ui.screens.player

import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import java.net.URLDecoder

@Composable
fun PlayerScreen(
    contentId: Int,
    contentType: String,
    videoUrl: String,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    var player by remember { mutableStateOf<ExoPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0L) }
    var playbackSpeed by remember { mutableStateOf(1f) }
    
    // Decode URL if encoded
    val decodedUrl = try {
        URLDecoder.decode(videoUrl, "UTF-8")
    } catch (e: Exception) {
        videoUrl
    }

    // Initialize player
    LaunchedEffect(Unit) {
        player = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(decodedUrl))
            prepare()
            playWhenReady = true
            
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    when (state) {
                        Player.STATE_READY -> isPlaying = true
                        Player.STATE_BUFFERING -> {}
                        Player.STATE_ENDED -> isPlaying = false
                        Player.STATE_IDLE -> {}
                    }
                }
                
                override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                    isPlaying = isPlayingNow
                }
            })
        }
        
        // TODO: Load last playback position from WatchHistoryDao
        // viewModel.loadLastPosition(contentId, contentType)?.let { position ->
        //     player?.seekTo(position)
        // }
    }

    // Save position on exit
    DisposableEffect(Unit) {
        onDispose {
            val position = player?.currentPosition ?: 0L
            // TODO: Save to WatchHistoryDao
            // viewModel.saveWatchHistory(contentId, contentType, position)
            player?.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Video player
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    this.player = player
                    useController = true
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Top bar
        TopAppBar(
            title = { Text("Watching") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
            ),
            modifier = Modifier.align(Alignment.TopCenter)
        )

        // Controls overlay (optional custom controls)
        // TODO: Add custom controls for quality toggle, subtitles, playback speed
    }
}

// TODO: Create PlayerViewModel for handling playback state, subtitles, quality selection
// TODO: Implement subtitle loading (SRT/VTT)
// TODO: Implement quality selection logic
// TODO: Implement playback speed control UI
