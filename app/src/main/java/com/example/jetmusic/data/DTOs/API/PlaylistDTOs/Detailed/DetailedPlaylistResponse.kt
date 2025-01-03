package com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed

import com.example.jetmusic.data.DTOs.API.HeadersResponse
import kotlinx.serialization.Serializable

@Serializable
class DetailedPlaylistResponse(
    val headers: HeadersResponse,
    val results: List<DetailedPlaylistObject>
)