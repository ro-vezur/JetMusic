package com.example.jetmusic.domain.usecases.api.musicAPI.playlist

import com.example.jetmusic.OFFSET_PER_PAGE
import com.example.jetmusic.data.Remote.API.ApiService
import javax.inject.Inject

class SearchPlaylistsUseCase @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke(query: String, offset: Int, limit: Int = OFFSET_PER_PAGE) =
        apiService.searchPlaylists(
            query = query,
            offset = offset,
            limit = limit,
        )
}