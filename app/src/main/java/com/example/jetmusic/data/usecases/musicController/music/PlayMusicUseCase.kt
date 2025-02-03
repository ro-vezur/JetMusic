package com.example.jetmusic.data.usecases.musicController.music

import com.example.jetmusic.domain.service.MusicController
import javax.inject.Inject

class PlayMusicUseCase @Inject constructor(private  val musicController: MusicController) {
    operator fun invoke(mediaItemIndex: Int) {
        musicController.select(mediaItemIndex)
    }
}