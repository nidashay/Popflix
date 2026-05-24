package com.popflix.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Search : Screen("search")
    object Categories : Screen("categories")
    object Watchlist : Screen("watchlist")
    object Settings : Screen("settings")
    object Credits : Screen("credits")
    object Details : Screen("details/{contentId}/{contentType}") {
        fun createRoute(contentId: Int, contentType: String) = "details/$contentId/$contentType"
    }
    object Player : Screen("player/{contentId}/{contentType}/{videoUrl}") {
        fun createRoute(contentId: Int, contentType: String, videoUrl: String) = 
            "player/$contentId/$contentType/${java.net.URLEncoder.encode(videoUrl, Charsets.UTF_8.name())}"
    }
}
