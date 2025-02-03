package com.example.jetmusic.data.usecases.api.musicAPI.playlist

import com.example.jetmusic.data.Remote.API.ApiService
import javax.inject.Inject

class PlaylistByIdUseCase @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke(id: String) = apiService.playlistById(id)
}