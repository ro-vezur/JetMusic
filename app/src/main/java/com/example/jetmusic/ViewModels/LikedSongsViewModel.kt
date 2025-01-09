package com.example.jetmusic.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.domain.usecases.api.musicAPI.music.MusicByIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikedSongsViewModel @Inject constructor(
    private val musicByIdUseCase: MusicByIdUseCase,
): ViewModel() {

    private val _likedSongsList: MutableStateFlow<MutableList<MusicObject>> = MutableStateFlow(mutableListOf())
    val likedSongsList: StateFlow<MutableList<MusicObject>> = _likedSongsList.asStateFlow()

    fun setSongsList(likedSongsList: MutableList<String>) = viewModelScope.launch {
        val musicListToSet = mutableListOf<MusicObject>()

        likedSongsList.forEach { id ->
            val musicResponse = musicByIdUseCase(id)
            if(musicResponse.results.isNotEmpty()) {
                val music = musicResponse.results.first()

                musicListToSet.add(music)
            }
        }

        _likedSongsList.emit(musicListToSet)
    }

    fun removeSong(id: String) = viewModelScope.launch {
        val newLikedSongsList = _likedSongsList.value
        newLikedSongsList.removeAll { it.id == id }
        _likedSongsList.emit(newLikedSongsList)
    }
}