package com.example.jetmusic.data.usecases.musicController.music

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.domain.service.MusicController
import javax.inject.Inject

class SkipToPreviousMusicUseCase @Inject constructor(private val musicController: MusicController) {
    operator fun invoke(updateHomeUi: (MusicObject?) -> Unit) {
        musicController.skipToPreviousSong()
        updateHomeUi(musicController.getCurrentMusic())
    }
}