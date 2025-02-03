package com.example.jetmusic.data.usecases.api.musicAPI.artist

import com.example.jetmusic.data.Remote.API.ApiService
import javax.inject.Inject

class ArtistByIdUseCase @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke(id: String) = apiService.artistById(id)

}