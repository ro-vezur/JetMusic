package com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class DetailedPlaylistObject (
    val id: String,
    val name: String,
    @SerializedName("user_name") val userName: String,
    val tracks: List<MusicObject>,
) {
    constructor(tracks: List<MusicObject>): this("","","",tracks)
}