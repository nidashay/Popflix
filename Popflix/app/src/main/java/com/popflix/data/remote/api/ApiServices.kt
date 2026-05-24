package com.popflix.data.remote.api

import com.popflix.data.remote.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// TODO: Replace base URL with actual TMDB API base URL: "https://api.themoviedb.org/3/"
interface TmdbApiService {
    
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): MovieResponse
    
    @GET("movie/trending")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "week",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): MovieResponse
    
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): MovieResponse
    
    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): TvShowResponse
    
    @GET("tv/trending")
    suspend fun getTrendingTvShows(
        @Path("time_window") timeWindow: String = "week",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): TvShowResponse
    
    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): TvShowResponse
    
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): MovieResponse
    
    @GET("search/tv")
    suspend fun searchTvShows(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): TvShowResponse
    
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailResponse
    
    @GET("tv/{tv_id}")
    suspend fun getTvShowDetails(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String
    ): TvShowDetailResponse
    
    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): CreditsResponse
    
    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCredits(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String
    ): CreditsResponse
    
    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("api_key") apiKey: String
    ): SeasonDetailResponse
    
    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String
    ): GenreResponse
    
    @GET("genre/tv/list")
    suspend fun getTvGenres(
        @Query("api_key") apiKey: String
    ): GenreResponse
    
    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): MovieResponse
    
    @GET("discover/tv")
    suspend fun getTvShowsByGenre(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): TvShowResponse
}

// Video Source APIs - These are placeholders for actual streaming source APIs
// TODO: Replace with actual server URLs and implement proper authentication

interface MegaCloudApiService {
    // TODO: Replace with actual Mega Cloud endpoint
    // Example: @GET("embed/ajax.php")
    @GET("embed/movie")
    suspend fun getMovieEmbed(
        @Query("id") movieId: String,
        // TODO: Add required auth headers/tokens
    ): MegaCloudResponse
    
    @GET("embed/tv")
    suspend fun getTvEmbed(
        @Query("id") tvId: String,
        @Query("season") season: Int,
        @Query("episode") episode: Int,
        // TODO: Add required auth headers/tokens
    ): MegaCloudResponse
}

interface WootlyApiService {
    // TODO: Replace with actual Wootly endpoint
    @GET("v/{id}")
    suspend fun getVideoSource(
        @Path("id") videoId: String,
        // TODO: Add required auth headers/tokens
    ): WootlyResponse
}

interface VidSrcApiService {
    // TODO: Replace with actual VidSrc endpoint
    @GET("embed/movie/{id}")
    suspend fun getMovieEmbed(
        @Path("id") movieId: Int,
        // TODO: Add required auth headers/tokens
    ): VidSrcResponse
    
    @GET("embed/tv/{id}/{season}/{episode}")
    suspend fun getTvEmbed(
        @Path("id") tvId: Int,
        @Path("season") season: Int,
        @Path("episode") episode: Int,
        // TODO: Add required auth headers/tokens
    ): VidSrcResponse
}

interface MfcApiService {
    // TODO: Replace with actual My Family Cinema endpoint
    @GET("api/v1/sources")
    suspend fun getSources(
        @Query("type") type: String,
        @Query("id") id: Int,
        // TODO: Add required auth headers/tokens
    ): MfcResponse
}
