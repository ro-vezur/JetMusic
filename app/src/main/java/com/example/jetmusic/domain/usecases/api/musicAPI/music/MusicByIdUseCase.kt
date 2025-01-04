package com.example.jetmusic.domain.usecases.api.musicAPI.music

import com.example.jetmusic.data.Remote.API.ApiService
import javax.inject.Inject


class MusicByIdUseCase @Inject constructor(private val apiService: ApiService) {
    suspend operator fun invoke(id: String) = apiService.musicById(id)
}