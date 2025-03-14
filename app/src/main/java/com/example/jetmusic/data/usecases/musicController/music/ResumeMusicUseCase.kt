package com.example.jetmusic.data.usecases.musicController.music

import com.example.jetmusic.domain.service.MusicController
import javax.inject.Inject

class ResumeMusicUseCase @Inject constructor(
    private val musicController: MusicController
) {
    operator fun invoke() = musicController.resume()
}