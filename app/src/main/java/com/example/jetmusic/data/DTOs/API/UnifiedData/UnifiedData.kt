package com.example.jetmusic.data.DTOs.API.UnifiedData

import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Simplified.SimplifiedArtistObject
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Simplified.SimplifiedPlaylistObject

data class UnifiedData(
    val id: String,
    val name: String,
    val image: String?,
    val type: UnifiedDataTypes,
) {
    companion object {
        fun fromMusic( music: MusicObject): UnifiedData {
            return UnifiedData(
                id = music.id,
                name = music.name,
                image = music.image,
                type = UnifiedDataTypes.MUSIC
            )
        }

        fun fromArtist( artistObject: SimplifiedArtistObject): UnifiedData {
            return UnifiedData(
                id = artistObject.id,
                name = artistObject.name,
                image = artistObject.image,
                type = UnifiedDataTypes.ARTIST
            )
        }

        fun fromPlaylist( playlistObject: SimplifiedPlaylistObject): UnifiedData {
            return UnifiedData(
                id = playlistObject.id,
                name = playlistObject.name,
                image = "",
                type = UnifiedDataTypes.PLAYLIST,
            )
        }
    }
}
