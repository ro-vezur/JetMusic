package com.example.jetmusic.data.DTOs.API.MusicDTOs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusicObject(
    val id: String,
    val name: String,
    val artist_id: String?,
    val artist_name: String?,
    val album_id: String,
    val album_name: String?,
    val duration: Int,
    val audio: String?,
  //  @SerialName("audiodownload") val downloadUrl: String?,
    val image: String?,
  //  @SerialName("audiodownload_allowed") val downloadAllowed: Boolean,
)