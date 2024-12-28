package com.example.jetmusic.data.DTOs.API.MusicDTOs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusicObject(
    val id: String,
    val name: String,
    val duration: Int,
    val artist_id: String,
    val artist_name: String,
    val audio: String,
    @SerialName("audiodownload") val downloadUrl: String?,
    val image: String,
    @SerialName("audiodownload_allowed") val downloadAllowed: Boolean,
)