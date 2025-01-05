package com.example.jetmusic.Helpers

import android.util.Log
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject

class MusicObjectHelper(private val musicObject: MusicObject) {
    fun musicArtistName(): String {
        musicObject.apply {

            return when {
                !artist_name.isNullOrBlank() && !album_name.isNullOrBlank() -> "$artist_name - $album_name"
                !artist_name.isNullOrBlank() -> artist_name
                !album_name.isNullOrBlank() -> album_name
                else -> ""
            }
        }
    }

}