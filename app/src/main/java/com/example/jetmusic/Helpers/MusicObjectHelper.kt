package com.example.jetmusic.Helpers

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject

class MusicObjectHelper(private val musicObject: MusicObject) {
    fun musicArtistName(): String {
        return if(musicObject.artist_name.isNullOrBlank()) "" else musicObject.artist_name +
                if(!musicObject.artist_name.isNullOrBlank() && !musicObject.album_name.isNullOrBlank()) " - " else "" +
                        if(musicObject.album_name.isNullOrBlank()) musicObject.album_name else ""
    }

}