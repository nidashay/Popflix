package com.popflix.data.source

import com.popflix.domain.model.SourceType
import com.popflix.domain.model.StreamResult
import retrofit2.HttpException
import java.io.IOException

/**
 * VideoSource interface defines the contract for all video streaming adapters.
 * Implementations should handle fetching embed/stream URLs from different providers.
 */
interface VideoSource {
    val sourceType: SourceType
    val priority: Int // Lower number = higher priority
    
    /**
     * Fetch video stream URL for a movie
     * @param movieId The movie ID (TMDB or provider-specific)
     * @param imdbId Optional IMDB ID for better matching
     * @return StreamResult containing success status and video URL or error
     */
    suspend fun getMovieStream(movieId: Int, imdbId: String? = null): StreamResult
    
    /**
     * Fetch video stream URL for a TV show episode
     * @param tvId The TV show ID
     * @param season Season number
     * @param episode Episode number
     * @return StreamResult containing success status and video URL or error
     */
    suspend fun getTvStream(tvId: Int, season: Int, episode: Int): StreamResult
    
    /**
     * Check if this source is available/healthy
     */
    suspend fun isAvailable(): Boolean
}

/**
 * Base implementation for VideoSource with common error handling
 */
abstract class BaseVideoSource(
    override val sourceType: SourceType,
    override val priority: Int
) : VideoSource {
    
    protected fun handleException(e: Exception): StreamResult {
        return when (e) {
            is HttpException -> {
                when (e.code()) {
                    404 -> StreamResult(
                        success = false,
                        errorMessage = "Content not found on ${sourceType.name}",
                        sourceType = sourceType
                    )
                    401, 403 -> StreamResult(
                        success = false,
                        errorMessage = "Authentication failed for ${sourceType.name}",
                        sourceType = sourceType
                    )
                    500, 502, 503 -> StreamResult(
                        success = false,
                        errorMessage = "Server error on ${sourceType.name}",
                        sourceType = sourceType
                    )
                    else -> StreamResult(
                        success = false,
                        errorMessage = "HTTP ${e.code()} error from ${sourceType.name}",
                        sourceType = sourceType
                    )
                }
            }
            is IOException -> StreamResult(
                success = false,
                errorMessage = "Network error connecting to ${sourceType.name}",
                sourceType = sourceType
            )
            is java.net.SocketTimeoutException -> StreamResult(
                success = false,
                errorMessage = "Request timed out for ${sourceType.name}",
                sourceType = sourceType
            )
            else -> StreamResult(
                success = false,
                errorMessage = "Error: ${e.message ?: "Unknown error"} from ${sourceType.name}",
                sourceType = sourceType
            )
        }
    }
}

/**
 * Mega Cloud Video Source Adapter
 * TODO: Replace placeholder implementation with actual Mega Cloud API integration
 */
class MegaCloudSource(
    private val apiService: com.popflix.data.remote.api.MegaCloudApiService
) : BaseVideoSource(SourceType.MEGA_CLOUD, priority = 1) {
    
    override suspend fun getMovieStream(movieId: Int, imdbId: String?): StreamResult {
        return try {
            // TODO: Implement actual Mega Cloud API call
            // val response = apiService.getMovieEmbed(movieId.toString())
            // if (response.sources?.isNotEmpty() == true) {
            //     StreamResult(
            //         success = true,
            //         videoUrl = response.sources.first().url,
            //         sourceType = sourceType
            //     )
            // } else {
            //     StreamResult(success = false, errorMessage = "No sources available", sourceType = sourceType)
            // }
            
            // Placeholder - simulate API call
            StreamResult(
                success = false,
                errorMessage = "Mega Cloud: TODO - Implement actual API integration",
                sourceType = sourceType
            )
        } catch (e: Exception) {
            handleException(e)
        }
    }
    
    override suspend fun getTvStream(tvId: Int, season: Int, episode: Int): StreamResult {
        return try {
            // TODO: Implement actual Mega Cloud API call for TV shows
            StreamResult(
                success = false,
                errorMessage = "Mega Cloud: TODO - Implement TV stream API",
                sourceType = sourceType
            )
        } catch (e: Exception) {
            handleException(e)
        }
    }
    
    override suspend fun isAvailable(): Boolean {
        // TODO: Implement health check
        return true
    }
}

/**
 * Wootly Video Source Adapter
 * TODO: Replace placeholder implementation with actual Wootly API integration
 */
class WootlySource(
    private val apiService: com.popflix.data.remote.api.WootlyApiService
) : BaseVideoSource(SourceType.WOOTLY, priority = 2) {
    
    override suspend fun getMovieStream(movieId: Int, imdbId: String?): StreamResult {
        return try {
            // TODO: Implement actual Wootly API call
            StreamResult(
                success = false,
                errorMessage = "Wootly: TODO - Implement actual API integration",
                sourceType = sourceType
            )
        } catch (e: Exception) {
            handleException(e)
        }
    }
    
    override suspend fun getTvStream(tvId: Int, season: Int, episode: Int): StreamResult {
        return try {
            // TODO: Implement actual Wootly API call for TV shows
            StreamResult(
                success = false,
                errorMessage = "Wootly: TODO - Implement TV stream API",
                sourceType = sourceType
            )
        } catch (e: Exception) {
            handleException(e)
        }
    }
    
    override suspend fun isAvailable(): Boolean {
        // TODO: Implement health check
        return true
    }
}

/**
 * VidSrc Video Source Adapter
 * TODO: Replace placeholder implementation with actual VidSrc API integration
 */
class VidSrcSource(
    private val apiService: com.popflix.data.remote.api.VidSrcApiService
) : BaseVideoSource(SourceType.VIDSRC, priority = 3) {
    
    override suspend fun getMovieStream(movieId: Int, imdbId: String?): StreamResult {
        return try {
            // TODO: Implement actual VidSrc API call
            StreamResult(
                success = false,
                errorMessage = "VidSrc: TODO - Implement actual API integration",
                sourceType = sourceType
            )
        } catch (e: Exception) {
            handleException(e)
        }
    }
    
    override suspend fun getTvStream(tvId: Int, season: Int, episode: Int): StreamResult {
        return try {
            // TODO: Implement actual VidSrc API call for TV shows
            StreamResult(
                success = false,
                errorMessage = "VidSrc: TODO - Implement TV stream API",
                sourceType = sourceType
            )
        } catch (e: Exception) {
            handleException(e)
        }
    }
    
    override suspend fun isAvailable(): Boolean {
        // TODO: Implement health check
        return true
    }
}

/**
 * My Family Cinema Video Source Adapter
 * TODO: Replace placeholder implementation with actual MFC API integration
 */
class MfcSource(
    private val apiService: com.popflix.data.remote.api.MfcApiService
) : BaseVideoSource(SourceType.MFC, priority = 4) {
    
    override suspend fun getMovieStream(movieId: Int, imdbId: String?): StreamResult {
        return try {
            // TODO: Implement actual MFC API call
            StreamResult(
                success = false,
                errorMessage = "MFC: TODO - Implement actual API integration",
                sourceType = sourceType
            )
        } catch (e: Exception) {
            handleException(e)
        }
    }
    
    override suspend fun getTvStream(tvId: Int, season: Int, episode: Int): StreamResult {
        return try {
            // TODO: Implement actual MFC API call for TV shows
            StreamResult(
                success = false,
                errorMessage = "MFC: TODO - Implement TV stream API",
                sourceType = sourceType
            )
        } catch (e: Exception) {
            handleException(e)
        }
    }
    
    override suspend fun isAvailable(): Boolean {
        // TODO: Implement health check
        return true
    }
}

/**
 * VideoSourceManager handles auto-fallback between different video sources.
 * If one source fails or returns 404, it automatically tries the next available source.
 */
class VideoSourceManager(
    private val sources: List<VideoSource>
) {
    
    init {
        // Sort by priority (lower number = higher priority)
        sources.sortedBy { it.priority }
    }
    
    /**
     * Get movie stream with automatic fallback
     * Tries each source in priority order until one succeeds
     */
    suspend fun getMovieStreamWithFallback(movieId: Int, imdbId: String? = null): StreamResult {
        val sortedSources = sources.sortedBy { it.priority }
        var lastError: StreamResult? = null
        
        for (source in sortedSources) {
            // Skip unavailable sources
            if (!source.isAvailable()) {
                continue
            }
            
            val result = source.getMovieStream(movieId, imdbId)
            
            if (result.success && result.videoUrl != null) {
                return result
            }
            
            lastError = result
        }
        
        // All sources failed, return the last error
        return lastError ?: StreamResult(
            success = false,
            errorMessage = "No video sources available"
        )
    }
    
    /**
     * Get TV stream with automatic fallback
     */
    suspend fun getTvStreamWithFallback(tvId: Int, season: Int, episode: Int): StreamResult {
        val sortedSources = sources.sortedBy { it.priority }
        var lastError: StreamResult? = null
        
        for (source in sortedSources) {
            if (!source.isAvailable()) {
                continue
            }
            
            val result = source.getTvStream(tvId, season, episode)
            
            if (result.success && result.videoUrl != null) {
                return result
            }
            
            lastError = result
        }
        
        return lastError ?: StreamResult(
            success = false,
            errorMessage = "No video sources available"
        )
    }
}
