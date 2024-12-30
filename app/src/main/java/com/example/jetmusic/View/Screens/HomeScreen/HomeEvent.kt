package com.example.jetmusic.View.Screens.HomeScreen

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject

sealed class HomeEvent {
    data class AddMediaItems(val musicList: List<MusicObject>): HomeEvent()
    object PlaySong : HomeEvent()
    object PauseSong : HomeEvent()
    object ResumeSong : HomeEvent()
    object SkipToNextSong : HomeEvent()
    object SkipToPreviousSong : HomeEvent()
    data class OnSongSelected(val selectedMusic: MusicObject) : HomeEvent()
}