package com.popflix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.popflix.data.local.dao.*
import com.popflix.data.local.entity.*

@Database(
    entities = [
        WatchlistEntity::class,
        WatchHistoryEntity::class,
        SearchHistoryEntity::class,
        CachedMovieEntity::class,
        CachedTvShowEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PopflixDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
    abstract fun watchHistoryDao(): WatchHistoryDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun movieCacheDao(): MovieCacheDao
    abstract fun tvShowCacheDao(): TvShowCacheDao
    
    companion object {
        const val DATABASE_NAME = "popflix_db"
    }
}
