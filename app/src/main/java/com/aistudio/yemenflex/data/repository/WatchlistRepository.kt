package com.aistudio.yemenflex.data.repository

import com.aistudio.yemenflex.data.api.MediaItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class WatchlistRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collectionRef = db.collection("watchlist")

    suspend fun getWatchlist(): List<MediaItem> {
        return try {
            val snapshot = collectionRef.get().await()
            snapshot.documents.mapNotNull { it.toObject(MediaItemDto::class.java)?.toMediaItem() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addToWatchlist(item: MediaItem) {
        val dto = MediaItemDto(
            id = item.id,
            title = item.title,
            name = item.name,
            posterPath = item.posterPath,
            backdropPath = item.backdropPath,
            overview = item.overview,
            voteAverage = item.voteAverage,
            releaseDate = item.releaseDate,
            firstAirDate = item.firstAirDate,
            mediaType = item.mediaType
        )
        collectionRef.document(item.id.toString()).set(dto).await()
    }

    suspend fun removeFromWatchlist(id: Int) {
        collectionRef.document(id.toString()).delete().await()
    }

    suspend fun isSaved(id: Int): Boolean {
        return try {
            val doc = collectionRef.document(id.toString()).get().await()
            doc.exists()
        } catch (e: Exception) {
            false
        }
    }
}

data class MediaItemDto(
    val id: Int = 0,
    val title: String? = null,
    val name: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val overview: String? = null,
    val voteAverage: Double? = null,
    val releaseDate: String? = null,
    val firstAirDate: String? = null,
    val mediaType: String? = null
) {
    fun toMediaItem() = MediaItem(
        id = id,
        title = title,
        name = name,
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        firstAirDate = firstAirDate,
        mediaType = mediaType
    )
}
