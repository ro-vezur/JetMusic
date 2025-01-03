package com.example.jetmusic.data.DTOs.API.ArtistDTOs

import com.example.jetmusic.data.DTOs.API.HeadersResponse

data class ArtistResponse(
    val headers: HeadersResponse,
    val results: List<ArtistObject>,
)
