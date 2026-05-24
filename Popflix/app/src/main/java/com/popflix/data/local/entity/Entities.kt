package com.popflix.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val contentType: String, // "movie" or "tv"
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "watch_history")
data class WatchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val historyId: Int = 0,
    val contentId: Int,
    val title: String,
    val posterPath: String?,
    val contentType: String, // "movie" or "tv"
    val seasonNumber: Int?,
    val episodeNumber: Int?,
    val playbackPosition: Long,
    val duration: Long,
    val watchedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val searchId: Int = 0,
    val query: String,
    val searchedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "cached_movies")
data class CachedMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val genreIds: String, // JSON encoded list
    val runtime: Int?,
    val originalLanguage: String,
    val popularity: Double,
    val cachedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "cached_tv_shows")
data class CachedTvShowEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val firstAirDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val genreIds: String, // JSON encoded list
    val episodeRunTime: String?, // JSON encoded list
    val originalLanguage: String,
    val popularity: Double,
    val cachedAt: Long = System.currentTimeMillis()
)
