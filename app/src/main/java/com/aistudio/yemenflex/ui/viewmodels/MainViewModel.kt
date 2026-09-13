package com.aistudio.yemenflex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aistudio.yemenflex.data.api.MediaItem
import com.aistudio.yemenflex.data.api.RetrofitClient
import com.aistudio.yemenflex.data.repository.WatchlistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val api = RetrofitClient.tmdbApi
    private val repo = WatchlistRepository()

    private val _trending = MutableStateFlow<List<MediaItem>>(emptyList())
    val trending: StateFlow<List<MediaItem>> = _trending.asStateFlow()
    
    private val _movies = MutableStateFlow<List<MediaItem>>(emptyList())
    val movies: StateFlow<List<MediaItem>> = _movies.asStateFlow()

    private val _series = MutableStateFlow<List<MediaItem>>(emptyList())
    val series: StateFlow<List<MediaItem>> = _series.asStateFlow()

    private val _searchResults = MutableStateFlow<List<MediaItem>>(emptyList())
    val searchResults: StateFlow<List<MediaItem>> = _searchResults.asStateFlow()

    private val _watchlist = MutableStateFlow<List<MediaItem>>(emptyList())
    val watchlist: StateFlow<List<MediaItem>> = _watchlist.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchHomeData()
        fetchWatchlist()
    }

    fun fetchHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _trending.value = api.getTrending().results
                _movies.value = api.getNowPlayingMovies().results
                _series.value = api.getOnTheAirTv().results
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun search(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _searchResults.value = api.searchMulti(query).results.filter { 
                    it.mediaType == "movie" || it.mediaType == "tv" 
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchWatchlist() {
        viewModelScope.launch {
            _watchlist.value = repo.getWatchlist()
        }
    }

    fun toggleWatchlist(item: MediaItem) {
        viewModelScope.launch {
            if (repo.isSaved(item.id)) {
                repo.removeFromWatchlist(item.id)
            } else {
                repo.addToWatchlist(item)
            }
            fetchWatchlist()
        }
    }
}
