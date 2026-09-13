package com.aistudio.yemenflex.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@Serializable
data class TmdbResponse<T>(
    val results: List<T>
)

@Serializable
data class MediaItem(
    val id: Int,
    val title: String? = null,
    val name: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    val overview: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null,
    @SerialName("media_type") val mediaType: String? = null
) {
    val displayTitle: String get() = title ?: name ?: "Unknown"
    val displayDate: String get() = releaseDate ?: firstAirDate ?: ""
    val posterUrl: String get() = "https://image.tmdb.org/t/p/w500${posterPath}"
    val backdropUrl: String get() = "https://image.tmdb.org/t/p/w1280${backdropPath}"
}

interface TmdbApi {
    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrending(
        @Path("media_type") mediaType: String = "all",
        @Path("time_window") timeWindow: String = "day",
        @Query("language") language: String = "ar-SA"
    ): TmdbResponse<MediaItem>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "ar-SA",
        @Query("page") page: Int = 1
    ): TmdbResponse<MediaItem>
    
    @GET("tv/on_the_air")
    suspend fun getOnTheAirTv(
        @Query("language") language: String = "ar-SA",
        @Query("page") page: Int = 1
    ): TmdbResponse<MediaItem>
    
    @GET("search/multi")
    suspend fun searchMulti(
        @Query("query") query: String,
        @Query("language") language: String = "ar-SA",
        @Query("page") page: Int = 1
    ): TmdbResponse<MediaItem>
}
