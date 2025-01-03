package com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Simplified

import kotlinx.serialization.Serializable

@Serializable
data class SimplifiedPlaylistObject(
    val id: String,
    val name: String,
)
