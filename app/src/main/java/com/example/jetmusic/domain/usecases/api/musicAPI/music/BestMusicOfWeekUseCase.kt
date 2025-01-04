package com.example.jetmusic.domain.usecases.api.musicAPI.music

import com.example.jetmusic.data.Remote.API.ApiService
import javax.inject.Inject

class BestMusicOfWeekUseCase @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke(tags: String, offset: Int) = apiService.bestMusicsOfWeek(
            tags = tags,
            offset = offset,
        )
}