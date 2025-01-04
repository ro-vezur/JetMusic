package com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import kotlinx.serialization.Serializable

@Serializable
data class DetailedArtistObject(
    val id: String,
    val name: String,
    val image: String,
    val tracks: List<MusicObject> = listOf()
)