package com.example.jetmusic.View.ScreenRoutes.ScreenRoutesAdditionalParameters

import android.os.Parcelable
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class DetailedPlaylistRouteParameters(
    val name: String,
    val image: String,
    val musicList: List<MusicObject> = listOf(),
)