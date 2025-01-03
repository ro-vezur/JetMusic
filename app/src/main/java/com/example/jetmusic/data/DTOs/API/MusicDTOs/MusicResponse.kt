package com.example.jetmusic.data.DTOs.API.MusicDTOs

import com.example.jetmusic.data.DTOs.API.HeadersResponse
import kotlinx.serialization.Serializable

@Serializable
data class MusicResponse(
    val headers: HeadersResponse,
    val results: List<MusicObject>
)
