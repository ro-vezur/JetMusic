package com.example.jetmusic.ViewModels.MainScreensViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.ArtistResponse
import com.example.jetmusic.data.DTOs.API.UnifiedData.UnifiedData
import com.example.jetmusic.data.Remote.Repositories.ApiRepostories.ArtistsRepository
import com.example.jetmusic.data.Remote.Repositories.ApiRepostories.SearchDataRepository
import com.example.jetmusic.Resources.ResultResource
import com.example.jetmusic.other.events.MusicSelectionEvent
import com.example.jetmusic.domain.usecases.musicController.music.AddMediaItemsUseCase
import com.example.jetmusic.domain.usecases.musicController.music.PauseMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.PlayMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.ResumeMusicUseCase
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
    private val searchDataRepository: SearchDataRepository,
    private val addMediaItemsUseCase: AddMediaItemsUseCase,
    private val playMusicUseCase: PlayMusicUseCase,
    private val pauseMusicUseCase: PauseMusicUseCase,
    private val resumeMusicUseCase: ResumeMusicUseCase,
): ViewModel() {

    private val _trendingArtists: MutableStateFlow<ResultResource<ArtistResponse>> = MutableStateFlow(ResultResource.Loading())
    val trendingArtists: StateFlow<ResultResource<ArtistResponse>> = _trendingArtists.asStateFlow()

    private val _isRequestSent: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRequestSent: StateFlow<Boolean> = _isRequestSent.asStateFlow()

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _searchedData: MutableStateFlow<PagingData<UnifiedData>> = MutableStateFlow(PagingData.empty())
    val searchedData: StateFlow<PagingData<UnifiedData>> = _searchedData.asStateFlow()

    init {
        setTrendingArtists()
    }

    fun onEvent(event: MusicSelectionEvent) {
        when (event) {
            is MusicSelectionEvent.AddMediaItems -> addMediaItemsUseCase(musicList = event.musicList)

            is MusicSelectionEvent.PlaySong ->  playMusicUseCase(event.musicList.indexOf(event.selectedMusic))

            MusicSelectionEvent.PauseSong -> pauseMusicUseCase()

            MusicSelectionEvent.ResumeSong -> resumeMusicUseCase()
        }
    }

    fun setTrendingArtists() = viewModelScope.launch {
        artistsRepository.getTrendingArtists().collectLatest { result ->
            _trendingArtists.emit(result)
        }
    }

    fun setRequestStatus(status: Boolean) = viewModelScope.launch {
        _isRequestSent.emit(status)
    }

    fun setSearchText(text: String) = viewModelScope.launch {
        _searchText.emit(text)
    }

    fun setSearchedData(limit: Int = 5) = viewModelScope.launch {
        val paginatedData = searchDataRepository.searchData(
            query = _searchText.value,
            limit = limit
        )

        paginatedData
            .cachedIn(viewModelScope)
            .collect {
                _searchedData.emit(it)
            }
    }

    fun clearSearchedData() = viewModelScope.launch {
        _searchedData.emit(PagingData.empty())
    }

    suspend fun getMusicById(id: String) = searchDataRepository.getMusicById(id)

}