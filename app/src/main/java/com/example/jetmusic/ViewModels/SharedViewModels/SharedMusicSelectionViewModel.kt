package com.example.jetmusic.ViewModels.SharedViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistObject
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistResponse
import com.example.jetmusic.domain.usecases.musicController.music.SetMediaItemsUseCase
import com.example.jetmusic.domain.usecases.musicController.music.PauseMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.PlayMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.ResumeMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SetMediaItemUseCase
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
): ViewModel() {

    private val _selectedPlaylist: MutableStateFlow<DetailedPlaylistObject?> = MutableStateFlow(null)
    val selectedPlaylist: StateFlow<DetailedPlaylistObject?> = _selectedPlaylist.asStateFlow()

    fun onEvent(event: MusicSelectionEvent) {
        when (event) {
            is MusicSelectionEvent.SetMediaItem -> setMediaItemUseCase(musicObject = event.musicObject)

            is MusicSelectionEvent.SetMediaItems -> setMediaItemsUseCase(musicList = event.musicList)

            is MusicSelectionEvent.PlaySong ->  playMusicUseCase(event.musicList.indexOf(event.selectedMusic))

            MusicSelectionEvent.PauseSong -> pauseMusicUseCase()

            MusicSelectionEvent.ResumeSong -> resumeMusicUseCase()
        }
    }

    fun setPlaylist(playlist: DetailedPlaylistObject?) = viewModelScope.launch {
        _selectedPlaylist.emit(playlist)
    }
}