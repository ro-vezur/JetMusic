package com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import kotlinx.serialization.Serializable

@Serializable
class DetailedPlaylistObject (
    val id: String,
    val name: String,
    val tracks: List<MusicObject>,
) {
    constructor(tracks: List<MusicObject>): this("","",tracks)
}