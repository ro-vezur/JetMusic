package com.example.jetmusic.data.DTOs.API.PlaylistDTOs

import kotlinx.serialization.Serializable

@Serializable
data class PlaylistObject(
    val id: String,
    val name: String,
)
