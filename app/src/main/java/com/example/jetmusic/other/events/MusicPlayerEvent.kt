package com.example.jetmusic.other.events

sealed class MusicPlayerEvent {

    data class SelectMusic(val mediaItemIndex: Int): MusicPlayerEvent()
    object PauseMusic : MusicPlayerEvent()
    object ResumeMusic : MusicPlayerEvent()
    object SkipToNextMusic : MusicPlayerEvent()
    object SkipToPreviousMusic : MusicPlayerEvent()
    data class SeekSongToPosition(val position: Long) : MusicPlayerEvent()
}