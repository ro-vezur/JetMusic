package com.example.jetmusic.other.events

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject

sealed class MusicSelectionEvent {
    data class SetMediaItem(val musicObject: MusicObject): MusicSelectionEvent()

    data class SetMediaItems(val musicList: List<MusicObject>): MusicSelectionEvent()
    data class SelectMusic(val musicList: List<MusicObject>, val selectedMusic: MusicObject) : MusicSelectionEvent()
    object PauseMusic : MusicSelectionEvent()
    object ResumeMusic : MusicSelectionEvent()
}