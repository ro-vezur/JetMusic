package com.example.jetmusic.domain.usecases.musicController.music

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.domain.service.MusicController
import javax.inject.Inject

class AddMediaItemsUseCase @Inject constructor(private val musicController: MusicController) {
    operator fun invoke(musicList: List<MusicObject>) {
        musicController.addMediaItems(musicList)
    }
}