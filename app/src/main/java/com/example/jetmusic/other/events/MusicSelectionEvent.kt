package com.example.jetmusic.other.events

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject

sealed class MusicSelectionEvent {
    data class AddMediaItems(val musicList: List<MusicObject>): MusicSelectionEvent()
    data class PlaySong(val musicList: List<MusicObject>, val selectedMusic: MusicObject) : MusicSelectionEvent()
    object PauseSong : MusicSelectionEvent()
    object ResumeSong : MusicSelectionEvent()
}