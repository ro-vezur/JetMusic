package com.example.jetmusic.domain.usecases.api.musicAPI.artist

import com.example.jetmusic.data.Remote.API.ApiService
import javax.inject.Inject

class TrendingArtistsUseCase @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke() = apiService.trendingArtists()
}