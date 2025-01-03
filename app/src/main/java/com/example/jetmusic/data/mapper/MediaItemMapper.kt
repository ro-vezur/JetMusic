package com.example.jetmusic.data.mapper

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject

fun MusicObject.toMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setMediaId(this.audio.toString())
        .setUri(this.audio)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setTitle(this.name)
                .setSubtitle(this.artist_name)
                .setArtist(this.artist_name)
                .setArtworkUri(Uri.parse(this.image))
                .build()
        )
        .build()
}