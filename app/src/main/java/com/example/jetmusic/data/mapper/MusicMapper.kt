package com.example.jetmusic.data.mapper

import androidx.media3.common.MediaItem
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject

fun MediaItem.toMusic() =
    MusicObject(
        id = mediaId,
        name = mediaMetadata.title.toString(),
        artist_id = "",
        artist_name = mediaMetadata.artist.toString(),
        album_id = "",
        album_name = mediaMetadata.albumArtist.toString(),
        duration = 0,
        audio = mediaId,
        image = mediaMetadata.artworkUri.toString(),
    )