package com.example.jetmusic.View.Screens.DetailedScreens.DetailedMusicScreen

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject

sealed class MusicEvent {
    object PauseSong : MusicEvent()
    object ResumeSong : MusicEvent()
    object SkipToNextSong : MusicEvent()
    object SkipToPreviousSong : MusicEvent()
    data class SeekSongToPosition(val position: Long) : MusicEvent()
}