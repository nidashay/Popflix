package com.popflix.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val genreIds: List<Int>,
    val runtime: Int?,
    val originalLanguage: String,
    val popularity: Double
)

data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val firstAirDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val genreIds: List<Int>,
    val episodeRunTime: List<Int>?,
    val originalLanguage: String,
    val popularity: Double
)

sealed class Content {
    abstract val id: Int
    abstract val title: String
    abstract val overview: String
    abstract val posterPath: String?
    abstract val backdropPath: String?
    abstract val releaseDate: String
    abstract val voteAverage: Double
    abstract val voteCount: Int
    abstract val genreIds: List<Int>
    abstract val originalLanguage: String
    abstract val popularity: Double
    
    data class MovieContent(val movie: Movie) : Content() {
        override val id = movie.id
        override val title = movie.title
        override val overview = movie.overview
        override val posterPath = movie.posterPath
        override val backdropPath = movie.backdropPath
        override val releaseDate = movie.releaseDate
        override val voteAverage = movie.voteAverage
        override val voteCount = movie.voteCount
        override val genreIds = movie.genreIds
        override val originalLanguage = movie.originalLanguage
        override val popularity = movie.popularity
    }
    
    data class TvShowContent(val tvShow: TvShow) : Content() {
        override val id = tvShow.id
        override val title = tvShow.name
        override val overview = tvShow.overview
        override val posterPath = tvShow.posterPath
        override val backdropPath = tvShow.backdropPath
        override val releaseDate = tvShow.firstAirDate
        override val voteAverage = tvShow.voteAverage
        override val voteCount = tvShow.voteCount
        override val genreIds = tvShow.genreIds
        override val originalLanguage = tvShow.originalLanguage
        override val popularity = tvShow.popularity
    }
}

data class Genre(
    val id: Int,
    val name: String
)

data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String?
)

data class Season(
    val id: Int,
    val name: String,
    val seasonNumber: Int,
    val episodeCount: Int,
    val posterPath: String?,
    val airDate: String
)

data class Episode(
    val id: Int,
    val name: String,
    val episodeNumber: Int,
    val seasonNumber: Int,
    val overview: String,
    val stillPath: String?,
    val airDate: String,
    val runtime: Int?
)

data class VideoSource(
    val id: String,
    val name: String,
    val embedUrl: String,
    val quality: String?,
    val type: SourceType
)

enum class SourceType {
    MEGA_CLOUD,
    WOOTLY,
    VIDSRC,
    MFC
}

data class StreamResult(
    val success: Boolean,
    val videoUrl: String? = null,
    val errorMessage: String? = null,
    val sourceType: SourceType? = null
)
