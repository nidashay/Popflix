package com.popflix.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    var scale by remember { mutableStateOf(0.5f) }
    var glowAlpha by remember { mutableStateOf(0f) }
    
    val infiniteTransition = rememberInfiniteTransition()
    val glowIntensity by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    LaunchedEffect(Unit) {
        // Fade in
        visible = true
        delay(100)
        
        // Scale up
        animateFloat(
            initialValue = 0.5f,
            targetValue = 1f,
            animationSpec = spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = Spring.DampingRatioMediumBouncy
            )
        ) { value ->
            scale = value
        }
        
        // Glow effect
        animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(500)
        ) { value ->
            glowAlpha = value
        }
        
        // Hold for a moment
        delay(800)
        
        // Scale down smoothly
        animateFloat(
            initialValue = 1f,
            targetValue = 0.9f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) { value ->
            scale = value
        }
        
        // Complete splash
        onSplashComplete()
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF0A0A0F)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Neon glow effect
        Box(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    alpha = glowAlpha * glowIntensity
                }
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFE50914).copy(alpha = 0.4f),
                            Color.Transparent
                        )
                    ),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )
        
        // Logo container
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    alpha = if (visible) 1f else 0f
                }
        ) {
            // Play icon integrated into "O"
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFE50914),
                                Color(0xFFB0060F)
                            )
                        ),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            ) {
                // Play triangle
                androidx.compose.material.icons.Icons.Filled.PlayArrow
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material.icons.Icons.Filled.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
                )
                
                // Neon ring
                androidx.compose.foundation.BorderStroke(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFF4757),
                            Color(0xFFFF6B7A)
                        )
                    )
                )
                androidx.compose.foundation.Border(
                    modifier = Modifier
                        .size(116.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFF4757).copy(alpha = 0.5f),
                                    Color.Transparent
                                )
                            ),
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // App name with neon effect
            androidx.compose.material3.Text(
                text = "Popflix",
                fontSize = 36.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .graphicsLayer {
                        alpha = glowAlpha
                    }
            )
            
            // Subtitle
            androidx.compose.material3.Text(
                text = "Stream Anytime, Anywhere",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.alpha(glowAlpha)
            )
        }
    }
}
