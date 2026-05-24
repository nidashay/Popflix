package com.popflix.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.popflix.ui.screens.categories.CategoriesScreen
import com.popflix.ui.screens.credits.CreditsScreen
import com.popflix.ui.screens.details.DetailsScreen
import com.popflix.ui.screens.home.HomeScreen
import com.popflix.ui.screens.player.PlayerScreen
import com.popflix.ui.screens.search.SearchScreen
import com.popflix.ui.screens.settings.SettingsScreen
import com.popflix.ui.screens.watchlist.WatchlistScreen

@Composable
fun PopflixNavHost() {
    val navController = rememberNavController()
    var startDestination by remember { mutableStateOf(Screen.Splash.route) }
    
    // Check if first launch or show splash
    LaunchedEffect(Unit) {
        // Simulate initial check (could be from DataStore)
        startDestination = Screen.Home.route
    }
    
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            com.popflix.ui.screens.SplashScreen(
                onSplashComplete = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDetails = { contentId, contentType ->
                    navController.navigate(Screen.Details.createRoute(contentId, contentType))
                },
                onNavigateToPlayer = { contentId, contentType, videoUrl ->
                    navController.navigate(Screen.Player.createRoute(contentId, contentType, videoUrl))
                }
            )
        }
        
        composable(Screen.Search.route) {
            SearchScreen(
                onNavigateToDetails = { contentId, contentType ->
                    navController.navigate(Screen.Details.createRoute(contentId, contentType))
                },
                onNavigateToPlayer = { contentId, contentType, videoUrl ->
                    navController.navigate(Screen.Player.createRoute(contentId, contentType, videoUrl))
                }
            )
        }
        
        composable(Screen.Categories.route) {
            CategoriesScreen(
                onNavigateToDetails = { contentId, contentType ->
                    navController.navigate(Screen.Details.createRoute(contentId, contentType))
                }
            )
        }
        
        composable(Screen.Watchlist.route) {
            WatchlistScreen(
                onNavigateToDetails = { contentId, contentType ->
                    navController.navigate(Screen.Details.createRoute(contentId, contentType))
                },
                onNavigateToPlayer = { contentId, contentType, videoUrl ->
                    navController.navigate(Screen.Player.createRoute(contentId, contentType, videoUrl))
                }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateToCredits = {
                    navController.navigate(Screen.Credits.route)
                }
            )
        }
        
        composable(Screen.Credits.route) {
            CreditsScreen()
        }
        
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("contentId") { type = NavType.IntType },
                navArgument("contentType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val contentId = backStackEntry.arguments?.getInt("contentId") ?: return@composable
            val contentType = backStackEntry.arguments?.getString("contentType") ?: return@composable
            
            DetailsScreen(
                contentId = contentId,
                contentType = contentType,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPlayer = { cId, cType, videoUrl ->
                    navController.navigate(Screen.Player.createRoute(cId, cType, videoUrl))
                }
            )
        }
        
        composable(
            route = Screen.Player.route,
            arguments = listOf(
                navArgument("contentId") { type = NavType.IntType },
                navArgument("contentType") { type = NavType.StringType },
                navArgument("videoUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val contentId = backStackEntry.arguments?.getInt("contentId") ?: return@composable
            val contentType = backStackEntry.arguments?.getString("contentType") ?: return@composable
            val videoUrl = backStackEntry.arguments?.getString("videoUrl") ?: return@composable
            
            PlayerScreen(
                contentId = contentId,
                contentType = contentType,
                videoUrl = videoUrl,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
