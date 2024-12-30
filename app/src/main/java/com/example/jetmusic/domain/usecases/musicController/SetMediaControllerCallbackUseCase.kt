package com.example.jetmusic.domain.usecases.musicController

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.domain.service.MusicController
import com.example.jetmusic.states.PlayerState
import javax.inject.Inject

class SetMediaControllerCallbackUseCase @Inject constructor(
    private val musicController: MusicController
) {
    operator fun invoke(
        callback: (
            playerState: PlayerState,
            currentSong: MusicObject?,
            currentPosition: Long,
            totalDuration: Long,
            isShuffleEnabled: Boolean,
            isRepeatOneEnabled: Boolean
        ) -> Unit
    ) {
        musicController.mediaControllerCallback = callback
    }
}