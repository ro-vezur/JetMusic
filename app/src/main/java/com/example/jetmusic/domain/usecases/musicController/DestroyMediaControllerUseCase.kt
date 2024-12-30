package com.example.jetmusic.domain.usecases.musicController

import com.example.jetmusic.domain.service.MusicController
import javax.inject.Inject

class DestroyMediaControllerUseCase @Inject constructor(
    private val musicController: MusicController
) {
    operator fun invoke() {
        musicController.destroy()
    }
}