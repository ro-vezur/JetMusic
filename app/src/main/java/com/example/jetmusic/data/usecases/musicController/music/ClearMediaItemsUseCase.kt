package com.example.jetmusic.data.usecases.musicController.music

import com.example.jetmusic.domain.service.MusicController
import javax.inject.Inject

class ClearMediaItemsUseCase @Inject constructor(
    private val mediaController: MusicController
) {
    operator fun invoke() {
        mediaController.clearMediaItems()
    }
}