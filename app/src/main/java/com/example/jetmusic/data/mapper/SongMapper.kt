package com.example.jetmusic.data.mapper

import androidx.media3.common.MediaItem
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject

fun MediaItem.toMusic() =
    MusicObject(
        id = mediaId,
        name = mediaMetadata.title.toString(),
        duration = 0,
        artist_id = "",
        artist_name = mediaMetadata.artist.toString(),
        audio = mediaId,
        downloadUrl = null,
        image = mediaMetadata.artworkUri.toString(),
        downloadAllowed = false
    )