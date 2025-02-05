package com.example.jetmusic.SharedViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.data.usecases.musicController.DestroyMediaControllerUseCase
import com.example.jetmusic.data.usecases.musicController.SetMediaControllerCallbackUseCase
import com.example.jetmusic.data.usecases.musicController.music.GetCurrentMusicPositionUseCase
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

    private val _musicControllerUiState: MutableStateFlow<MusicControllerUiState> = MutableStateFlow(
        MusicControllerUiState()
    )
    var musicControllerUiState: StateFlow<MusicControllerUiState> = _musicControllerUiState.asStateFlow()

    init {
        setMediaControllerCallback()
    }

    private fun setMediaControllerCallback() {
        setMediaControllerCallbackUseCase { playerState, currentSong, currentPosition, totalDuration,
                                            isShuffleEnabled, isRepeatOneEnabled ->
            viewModelScope.launch {
                _musicControllerUiState.emit(
                    musicControllerUiState.value.copy(
                        playerState = playerState,
                        currentMusic = currentSong,
                        currentPosition = currentPosition,
                        totalDuration = totalDuration,
                        isShuffleEnabled = isShuffleEnabled,
                        isRepeatOneEnabled = isRepeatOneEnabled
                    )
                )
            }

            if (playerState == PlayerState.PLAYING) {
                viewModelScope.launch {
                    while (true) {
                        delay(0.1.seconds)
                        _musicControllerUiState.emit(
                            musicControllerUiState.value.copy(
                                currentPosition = getCurrentMusicPositionUseCase()
                            )
                        )
                    }
                }
            }
        }
    }

    fun destroyMediaController() {
        destroyMediaControllerUseCase()
    }

}