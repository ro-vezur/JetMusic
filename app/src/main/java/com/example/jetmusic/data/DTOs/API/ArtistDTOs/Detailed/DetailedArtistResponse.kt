package com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed

import com.example.jetmusic.data.DTOs.API.HeadersResponse
import kotlinx.serialization.Serializable

@Serializable
data class DetailedArtistResponse(
    val headers: HeadersResponse,
    val results: List<DetailedArtistObject>
)
