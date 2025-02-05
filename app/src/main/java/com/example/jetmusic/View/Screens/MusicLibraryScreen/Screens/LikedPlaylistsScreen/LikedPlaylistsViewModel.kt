package com.example.jetmusic.View.Screens.MusicLibraryScreen.Screens.LikedPlaylistsScreen

import androidx.lifecycle.ViewModel
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistObject
import com.example.jetmusic.data.usecases.api.musicAPI.playlist.PlaylistByIdUseCase
import com.example.jetmusic.other.Resources.ResultResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class LikedPlaylistsViewModel @Inject constructor(
    val playlistByIdUseCase: PlaylistByIdUseCase,
): ViewModel() {

    private val _likedPlaylistResult: MutableStateFlow<ResultResource<List<DetailedPlaylistObject>>> =
        MutableStateFlow(ResultResource.Loading())
    val likedPlaylistsResult: StateFlow<ResultResource<List<DetailedPlaylistObject>>> = _likedPlaylistResult.asStateFlow()

    suspend fun setPlaylists(newPlaylistsIds: List<String>) = flow {
        val newPlaylistsList = mutableListOf<DetailedPlaylistObject>()

        emit(ResultResource.Loading())

        if(newPlaylistsIds.isEmpty()) {
            emit(ResultResource.Error(message = "You don't have any liked playlists"))
        } else {
            newPlaylistsIds.forEach { id ->
                val playlist = playlistByIdUseCase(id)

                if (playlist.results.isNotEmpty()) {
                    newPlaylistsList.add(playlist.results.first())
                    emit(ResultResource.Success(data = newPlaylistsList.toList()))
                }
            }
        }
    }.catch { e ->
        emit(ResultResource.Error(message = e.message.toString()))
    }.collectLatest { result ->
      _likedPlaylistResult.emit(result)
    }
}