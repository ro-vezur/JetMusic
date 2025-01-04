package com.example.jetmusic.data.DTOs.API.ArtistDTOs.Simplified

import com.example.jetmusic.data.DTOs.API.HeadersResponse

data class SimplifiedArtistResponse(
    val headers: HeadersResponse,
    val results: List<SimplifiedArtistObject>,
)
