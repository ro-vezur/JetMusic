package com.example.jetmusic.other.events

sealed class MusicPlayerEvent {

    data class PlaySong(val mediaItemIndex: Int): MusicPlayerEvent()
    object PauseSong : MusicPlayerEvent()
    object ResumeSong : MusicPlayerEvent()
    object SkipToNextSong : MusicPlayerEvent()
    object SkipToPreviousSong : MusicPlayerEvent()
    data class SeekSongToPosition(val position: Long) : MusicPlayerEvent()
}