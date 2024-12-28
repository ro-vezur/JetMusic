package com.example.jetmusic.data.DTOs.API.ArtistDTOs

import com.example.jetmusic.data.DTOs.API.ResponseHeaders

data class ArtistResponse(
    val headers: ResponseHeaders,
    val results: List<ArtistObject>,
)
