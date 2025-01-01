package com.example.jetmusic.ViewModels.SharedViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.PlaylistObject
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.domain.usecases.musicController.DestroyMediaControllerUseCase
import com.example.jetmusic.domain.usecases.musicController.SetMediaControllerCallbackUseCase
import com.example.jetmusic.domain.usecases.musicController.music.GetCurrentMusicPositionUseCase
import com.example.jetmusic.states.PlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SharedMusicControllerViewModel @Inject constructor(
    private val setMediaControllerCallbackUseCase: SetMediaControllerCallbackUseCase,
    private val getCurrentMusicPositionUseCase: GetCurrentMusicPositionUseCase,
    private val destroyMediaControllerUseCase: DestroyMediaControllerUseCase,
): ViewModel() {

    var musicControllerUiState by mutableStateOf(MusicControllerUiState())
        private set

    private val _selectedPlaylist: MutableStateFlow<List<MusicObject>> = MutableStateFlow(emptyList())
    val selectedPlaylist: StateFlow<List<MusicObject>> = _selectedPlaylist.asStateFlow()

    init {
        setMediaControllerCallback()
    }

    private fun setMediaControllerCallback() {
        setMediaControllerCallbackUseCase { playerState, currentSong, currentPosition, totalDuration,
                                            isShuffleEnabled, isRepeatOneEnabled ->
            musicControllerUiState = musicControllerUiState.copy(
                playerState = playerState,
                currentMusic = currentSong,
                currentPosition = currentPosition,
                totalDuration = totalDuration,
                isShuffleEnabled = isShuffleEnabled,
                isRepeatOneEnabled = isRepeatOneEnabled
            )

            if (playerState == PlayerState.PLAYING) {
                viewModelScope.launch {
                    while (true) {
                        delay(1.seconds)
                        musicControllerUiState = musicControllerUiState.copy(
                            currentPosition = getCurrentMusicPositionUseCase()
                        )
                    }
                }
            }
        }
    }

    fun destroyMediaController() {
        destroyMediaControllerUseCase()
    }

    fun setPlaylist(playlist: List<MusicObject>) = viewModelScope.launch {
        _selectedPlaylist.emit(playlist)
    }

}