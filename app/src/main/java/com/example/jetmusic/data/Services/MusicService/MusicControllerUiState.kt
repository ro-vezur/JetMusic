package com.example.jetmusic.data.Services.MusicService

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.states.PlayerState

data class MusicControllerUiState(
    val playerState: PlayerState? = null,
    val currentMusic: MusicObject? = null,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val isShuffleEnabled: Boolean = false,
    val isRepeatOneEnabled: Boolean = false
)