package com.example.jetmusic.domain.usecases.musicController.music

import com.example.jetmusic.domain.service.MusicController
import javax.inject.Inject

class GetCurrentMusicPositionUseCase @Inject constructor(
    private val musicController: MusicController
) {
    operator fun invoke() = musicController.getCurrentPosition()
}