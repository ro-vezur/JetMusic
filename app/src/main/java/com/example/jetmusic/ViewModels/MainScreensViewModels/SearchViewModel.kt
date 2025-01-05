package com.example.jetmusic.ViewModels.MainScreensViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetmusic.Helpers.Pagination.UnifiedDataPagingSource
import com.example.jetmusic.OFFSET_PER_PAGE
import com.example.jetmusic.PAGE_SIZE
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Simplified.SimplifiedArtistResponse
import com.example.jetmusic.data.DTOs.API.UnifiedData.UnifiedData
import com.example.jetmusic.other.Resources.ResultResource
import com.example.jetmusic.domain.usecases.api.musicAPI.artist.ArtistByIdUseCase
import com.example.jetmusic.domain.usecases.api.musicAPI.artist.SearchArtistsUseCase
import com.example.jetmusic.domain.usecases.api.musicAPI.artist.TrendingArtistsUseCase
import com.example.jetmusic.domain.usecases.api.musicAPI.music.MusicByIdUseCase
import com.example.jetmusic.domain.usecases.api.musicAPI.music.SearchMusicUseCase
import com.example.jetmusic.domain.usecases.api.musicAPI.playlist.PlaylistByIdUseCase
import com.example.jetmusic.domain.usecases.api.musicAPI.playlist.SearchPlaylistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trendingArtistsUseCase: TrendingArtistsUseCase,
    private val musicByIdUseCase: MusicByIdUseCase,
    private val playlistsByIdUseCase: PlaylistByIdUseCase,
    private val artistByIdUseCase: ArtistByIdUseCase,
    private val searchMusicUseCase: SearchMusicUseCase,
    private val searchArtistsUseCase: SearchArtistsUseCase,
    private val searchPlaylistsUseCase: SearchPlaylistsUseCase

): ViewModel() {

    private val _trendingArtists: MutableStateFlow<ResultResource<SimplifiedArtistResponse>> = MutableStateFlow(
        ResultResource.Loading())
    val trendingArtists: StateFlow<ResultResource<SimplifiedArtistResponse>> = _trendingArtists.asStateFlow()

    private val _isRequestSent: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRequestSent: StateFlow<Boolean> = _isRequestSent.asStateFlow()

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _searchedData: MutableStateFlow<PagingData<UnifiedData>> = MutableStateFlow(PagingData.empty())
    val searchedData: StateFlow<PagingData<UnifiedData>> = _searchedData.asStateFlow()

    init {
        setTrendingArtists()
    }

    fun setTrendingArtists() = viewModelScope.launch {
        flow {
            emit(ResultResource.Loading())

            val response = trendingArtistsUseCase()

            if(response.results.isNotEmpty()) {
                emit(ResultResource.Success(data = response))
            } else {
                emit(ResultResource.Error(message = "Results is empty"))
            }
        }.catch { e ->
            emit(ResultResource.Error(message = e.message.toString()))
        }.collectLatest { result ->
            _trendingArtists.emit(result)
        }
    }

    fun setRequestStatus(status: Boolean) = viewModelScope.launch {
        _isRequestSent.emit(status)
    }

    fun setSearchText(text: String) = viewModelScope.launch {
        _searchText.emit(text)
    }

    fun setSearchedData(limit: Int = OFFSET_PER_PAGE) = viewModelScope.launch {
        val paginatedData = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
            UnifiedDataPagingSource(
                getMusicResponse = { page -> searchMusicUseCase(_searchText.value,page * OFFSET_PER_PAGE,limit) },
                getArtistsResponse = { page -> searchArtistsUseCase(_searchText.value,page * OFFSET_PER_PAGE,limit) },
                getPlaylistResponse = { page -> searchPlaylistsUseCase(_searchText.value,page * OFFSET_PER_PAGE,limit) }
            )
        }.flow

        paginatedData
            .cachedIn(viewModelScope)
            .collect {
                _searchedData.emit(it)
            }
    }

    fun clearSearchedData() = viewModelScope.launch {
        _searchedData.emit(PagingData.empty())
    }

    suspend fun getMusicById(id: String) = musicByIdUseCase(id)

    suspend fun getPlaylistById(id: String) = playlistsByIdUseCase(id)

    suspend fun getArtistById(id: String) = artistByIdUseCase(id)
}