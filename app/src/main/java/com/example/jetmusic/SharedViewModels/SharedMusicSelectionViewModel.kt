package com.example.jetmusic.SharedViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistObject
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistObject
import com.example.jetmusic.data.DTOs.API.UnifiedData.MediaTypes
import com.example.jetmusic.data.usecases.musicController.music.ClearMediaItemsUseCase
import com.example.jetmusic.data.usecases.musicController.music.SetMediaItemsUseCase
import com.example.jetmusic.data.usecases.musicController.music.PauseMusicUseCase
import com.example.jetmusic.data.usecases.musicController.music.PlayMusicUseCase
import com.example.jetmusic.data.usecases.musicController.music.ResumeMusicUseCase
import com.example.jetmusic.data.usecases.musicController.music.SetMediaItemUseCase
import com.example.jetmusic.other.events.MusicSelectionEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedMusicSelectionViewModel @Inject constructor(
    private val setMediaItemUseCase: SetMediaItemUseCase,
    private val setMediaItemsUseCase: SetMediaItemsUseCase,
    private val playMusicUseCase: PlayMusicUseCase,
    private val pauseMusicUseCase: PauseMusicUseCase,
    private val resumeMusicUseCase: ResumeMusicUseCase,
    private val clearMediaItemsUseCase: ClearMediaItemsUseCase
): ViewModel() {

    private val _selectedMediaType: MutableStateFlow<MediaTypes> = MutableStateFlow(MediaTypes.MUSIC)
    val selectedMediaType: StateFlow<MediaTypes> = _selectedMediaType.asStateFlow()

    private val _selectedPlaylist: MutableStateFlow<DetailedPlaylistObject?> = MutableStateFlow(null)
    val selectedPlaylist: StateFlow<DetailedPlaylistObject?> = _selectedPlaylist.asStateFlow()

    private val _selectedArtist: MutableStateFlow<DetailedArtistObject?> = MutableStateFlow(null)
    val selectedArtist: StateFlow<DetailedArtistObject?> = _selectedArtist.asStateFlow()

    fun onEvent(event: MusicSelectionEvent) {
        when (event) {
            is MusicSelectionEvent.SetMediaItem -> setMediaItemUseCase(musicObject = event.musicObject)

            is MusicSelectionEvent.SetMediaItems -> setMediaItemsUseCase(musicList = event.musicList)

            is MusicSelectionEvent.SelectMusic ->  playMusicUseCase(event.musicIndex)

            MusicSelectionEvent.PauseMusic -> pauseMusicUseCase()

            MusicSelectionEvent.ResumeMusic -> resumeMusicUseCase()

            MusicSelectionEvent.ClearMediaItems -> clearMediaItemsUseCase()
        }
    }

    fun setPlaylist(playlist: DetailedPlaylistObject?) = viewModelScope.launch {
        _selectedPlaylist.emit(playlist)
    }

    fun setArtist(artistObject: DetailedArtistObject?) = viewModelScope.launch {
        _selectedArtist.emit(artistObject)
    }

    fun setMusic(musicObject: MusicObject) = viewModelScope.launch {
        _selectedPlaylist.emit(DetailedPlaylistObject(listOf(musicObject)))
    }

    fun setMediaType(mediaType: MediaTypes) = viewModelScope.launch {
        _selectedMediaType.emit(mediaType)
    }
}