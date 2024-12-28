package com.example.jetmusic.data.DTOs.API.MusicDTOs

import com.example.jetmusic.data.DTOs.API.ResponseHeaders
import kotlinx.serialization.Serializable

@Serializable
data class MusicResponse(
    val headers: ResponseHeaders,
    val results: List<MusicObject>
)
