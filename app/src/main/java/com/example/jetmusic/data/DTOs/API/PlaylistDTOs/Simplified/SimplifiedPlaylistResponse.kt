package com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Simplified

import com.example.jetmusic.data.DTOs.API.HeadersResponse
import kotlinx.serialization.Serializable

@Serializable
data class SimplifiedPlaylistResponse(
    val headers: HeadersResponse,
    val results: List<SimplifiedPlaylistObject>
)
