package com.popflix.data.local.dao

import androidx.room.*
import com.popflix.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist ORDER BY addedAt DESC")
    fun getAllWatchlist(): Flow<List<WatchlistEntity>>
    
    @Query("SELECT * FROM watchlist WHERE id = :id")
    suspend fun getById(id: Int): WatchlistEntity?
    
    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE id = :id)")
    suspend fun isWatchlisted(id: Int): Boolean
    
    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE id = :id)")
    fun isWatchlistedFlow(id: Int): Flow<Boolean>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchlist(entity: WatchlistEntity)
    
    @Delete
    suspend fun removeFromWatchlist(entity: WatchlistEntity)
    
    @Query("DELETE FROM watchlist WHERE id = :id")
    suspend fun removeById(id: Int)
}

@Dao
interface WatchHistoryDao {
    @Query("SELECT * FROM watch_history ORDER BY watchedAt DESC")
    fun getAllHistory(): Flow<List<WatchHistoryEntity>>
    
    @Query("SELECT * FROM watch_history WHERE contentId = :contentId AND contentType = :contentType ORDER BY watchedAt DESC LIMIT 1")
    suspend fun getLatestForContent(contentId: Int, contentType: String): WatchHistoryEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToHistory(entity: WatchHistoryEntity)
    
    @Query("DELETE FROM watch_history WHERE contentId = :contentId AND contentType = :contentType")
    suspend fun clearForContent(contentId: Int, contentType: String)
    
    @Query("DELETE FROM watch_history")
    suspend fun clearAllHistory()
}

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY searchedAt DESC LIMIT 20")
    fun getRecentSearches(): Flow<List<SearchHistoryEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearch(entity: SearchHistoryEntity)
    
    @Query("DELETE FROM search_history WHERE query = :query")
    suspend fun removeSearch(query: String)
    
    @Query("DELETE FROM search_history")
    suspend fun clearAllSearches()
}

@Dao
interface MovieCacheDao {
    @Query("SELECT * FROM cached_movies WHERE id = :id")
    suspend fun getMovie(id: Int): CachedMovieEntity?
    
    @Query("SELECT * FROM cached_movies ORDER BY cachedAt DESC LIMIT :limit")
    suspend fun getRecentMovies(limit: Int = 50): List<CachedMovieEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheMovie(entity: CachedMovieEntity)
    
    @Query("DELETE FROM cached_movies WHERE cachedAt < :thresholdTime")
    suspend fun clearOldCache(thresholdTime: Long)
}

@Dao
interface TvShowCacheDao {
    @Query("SELECT * FROM cached_tv_shows WHERE id = :id")
    suspend fun getTvShow(id: Int): CachedTvShowEntity?
    
    @Query("SELECT * FROM cached_tv_shows ORDER BY cachedAt DESC LIMIT :limit")
    suspend fun getRecentTvShows(limit: Int = 50): List<CachedTvShowEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheTvShow(entity: CachedTvShowEntity)
    
    @Query("DELETE FROM cached_tv_shows WHERE cachedAt < :thresholdTime")
    suspend fun clearOldCache(thresholdTime: Long)
}
