package com.example.jetmusic.data.DTOs.API.PlaylistDTOs

import com.example.jetmusic.data.DTOs.API.ResponseHeaders
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistResponse(
    val headers: ResponseHeaders,
    val results: List<PlaylistObject>
)
