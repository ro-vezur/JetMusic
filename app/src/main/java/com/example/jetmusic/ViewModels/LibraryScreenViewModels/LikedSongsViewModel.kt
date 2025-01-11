package com.example.jetmusic.ViewModels.LibraryScreenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.domain.usecases.api.musicAPI.music.MusicByIdUseCase
import com.example.jetmusic.other.Resources.ResultResource
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
class LikedSongsViewModel @Inject constructor(
    private val musicByIdUseCase: MusicByIdUseCase,
): ViewModel() {

    private val _likedSongsList: MutableStateFlow<ResultResource<List<MusicObject>>> =
        MutableStateFlow(ResultResource.Loading())
    val likedSongsList: StateFlow<ResultResource<List<MusicObject>>> = _likedSongsList.asStateFlow()

    suspend fun setSongsList(likedSongsList: List<String>) = flow {
        val musicListToSet = mutableListOf<MusicObject>()

        emit(ResultResource.Loading())

        if(likedSongsList.isEmpty()) {
            emit(ResultResource.Error(message = "You don't have any liked songs"))
        }
        likedSongsList.forEach { id ->
            val musicResponse = musicByIdUseCase(id)
            if(musicResponse.results.isNotEmpty()) {
                val music = musicResponse.results.first()

                musicListToSet.add(music)
                emit(ResultResource.Success(data = musicListToSet.toList()))
            }
        }
    }.catch { e ->
        emit(ResultResource.Error(message = e.message.toString()))
    }.collectLatest { result ->
        _likedSongsList.emit(result)
    }
}