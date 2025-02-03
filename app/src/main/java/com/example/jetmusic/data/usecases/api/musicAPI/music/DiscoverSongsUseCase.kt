package com.example.jetmusic.data.usecases.api.musicAPI.music

import com.example.jetmusic.data.Remote.API.ApiService
import com.example.jetmusic.data.Remote.API.TOTAL_POPULARITY
import javax.inject.Inject

class DiscoverSongsUseCase @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke(
        tags: String,
        offset: Int,
        order: String = TOTAL_POPULARITY
    ) = apiService.bestMusicsOfWeek(
        tags = tags,
        offset = offset,
        order = order
    )
}