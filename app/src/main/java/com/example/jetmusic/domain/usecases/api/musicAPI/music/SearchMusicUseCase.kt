package com.example.jetmusic.domain.usecases.api.musicAPI.music

import com.example.jetmusic.OFFSET_PER_PAGE
import com.example.jetmusic.data.Remote.API.ApiService
import javax.inject.Inject

class SearchMusicUseCase @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke(query: String, offset: Int, limit: Int = OFFSET_PER_PAGE) =
        apiService.searchMusic(
            query = query,
            offset = offset,
            limit = limit
        )
}