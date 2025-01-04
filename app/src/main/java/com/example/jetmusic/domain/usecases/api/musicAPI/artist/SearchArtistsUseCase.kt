package com.example.jetmusic.domain.usecases.api.musicAPI.artist

import com.example.jetmusic.OFFSET_PER_PAGE
import com.example.jetmusic.data.Remote.API.ApiService
import javax.inject.Inject

class SearchArtistsUseCase @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke(query: String, offset: Int, limit: Int = OFFSET_PER_PAGE) =
        apiService.searchArtists(
            query = query,
            offset = offset,
            limit = limit,
        )
}