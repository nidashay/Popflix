package com.popflix.data.remote.model

import com.google.gson.annotations.SerializedName

// TMDB Response Models
data class MovieResponse(
    val page: Int,
    val results: List<TmdbMovieResult>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class TvShowResponse(
    val page: Int,
    val results: List<TmdbTvResult>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class TmdbMovieResult(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    val popularity: Double,
    @SerializedName("original_language") val originalLanguage: String
)

data class TmdbTvResult(
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    val popularity: Double,
    @SerializedName("original_language") val originalLanguage: String
)

data class MovieDetailResponse(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    val genres: List<GenreDto>,
    val runtime: Int?,
    val popularity: Double,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("imdb_id") val imdbId: String?
)

data class TvShowDetailResponse(
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    val genres: List<GenreDto>,
    @SerializedName("episode_run_time") val episodeRunTime: List<Int>?,
    val popularity: Double,
    @SerializedName("original_language") val originalLanguage: String,
    val seasons: List<SeasonDto>,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int
)

data class GenreDto(
    val id: Int,
    val name: String
)

data class SeasonDto(
    val id: Int,
    val name: String,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("episode_count") val episodeCount: Int,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("air_date") val airDate: String?
)

data class CreditsResponse(
    val cast: List<CastDto>
)

data class CastDto(
    val id: Int,
    val name: String,
    val character: String,
    @SerializedName("profile_path") val profilePath: String?
)

data class SeasonDetailResponse(
    val id: Int,
    val name: String,
    @SerializedName("season_number") val seasonNumber: Int,
    val episodes: List<EpisodeDto>
)

data class EpisodeDto(
    val id: Int,
    val name: String,
    @SerializedName("episode_number") val episodeNumber: Int,
    @SerializedName("season_number") val seasonNumber: Int,
    val overview: String,
    @SerializedName("still_path") val stillPath: String?,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("runtime") val runtime: Int?
)

data class GenreResponse(
    val genres: List<GenreDto>
)

// Video Source Response Models

data class MegaCloudResponse(
    val sources: List<MegaCloudSource>?,
    val encrypted: Boolean? = false,
    val track: List<SubtitleTrack>? = null
)

data class MegaCloudSource(
    val url: String,
    val type: String? = null,
    val quality: String? = null
)

data class SubtitleTrack(
    val file: String,
    val label: String?,
    val kind: String? = "captions"
)

data class WootlyResponse(
    val data: WootlyData?
)

data class WootlyData(
    val videoUrl: String?,
    val quality: String?
)

data class VidSrcResponse(
    val url: String?,
    val quality: String? = "auto"
)

data class MfcResponse(
    val success: Boolean,
    val data: MfcData?
)

data class MfcData(
    val sources: List<MfcSource>?
)

data class MfcSource(
    val url: String,
    val quality: String?,
    val server: String
)
