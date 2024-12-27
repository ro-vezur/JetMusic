package com.example.jetmusic.ViewModels.MainScreensViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.DTOs.API.ArtistDTOs.ArtistResponse
import com.example.jetmusic.Remote.Repositories.ApiRepostories.ArtistsRepository
import com.example.jetmusic.Resources.ResultResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val artistsRepository: ArtistsRepository,
): ViewModel() {

    private val _trendingArtists: MutableStateFlow<ResultResource<ArtistResponse>> = MutableStateFlow(ResultResource.Loading())
    val trendingArtists: StateFlow<ResultResource<ArtistResponse>> = _trendingArtists.asStateFlow()

    init {
        setTrendingArtists()
    }

    fun setTrendingArtists() = viewModelScope.launch {
        artistsRepository.getTrendingArtists().collectLatest { result ->
            _trendingArtists.emit(result)
        }
    }

}