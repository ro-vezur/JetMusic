package com.example.jetmusic.ViewModels

import androidx.lifecycle.ViewModel
import com.example.jetmusic.other.events.MusicPlayerEvent
import com.example.jetmusic.domain.usecases.musicController.music.PauseMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.PlayMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.ResumeMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SeekMusicToPositionUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SkipToNextMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SkipToPreviousMusicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val playMusicUseCase: PlayMusicUseCase,
    private val pauseSongUseCase: PauseMusicUseCase,
    private val resumeSongUseCase: ResumeMusicUseCase,
    private val skipToNextSongUseCase: SkipToNextMusicUseCase,
    private val skipToPreviousSongUseCase: SkipToPreviousMusicUseCase,
    private val seekSongToPositionUseCase: SeekMusicToPositionUseCase,
) : ViewModel() {
    fun onEvent(event: MusicPlayerEvent) {
        when (event) {
            is MusicPlayerEvent.SelectMusic -> playMusicUseCase(event.mediaItemIndex)
            MusicPlayerEvent.PauseMusic -> pauseSongUseCase()
            MusicPlayerEvent.ResumeMusic -> resumeSongUseCase()
            is MusicPlayerEvent.SeekSongToPosition -> seekToPosition(event.position)
            MusicPlayerEvent.SkipToNextMusic -> skipToNextSongUseCase()
            MusicPlayerEvent.SkipToPreviousMusic -> skipToPreviousSong()
        }
    }

    private fun skipToPreviousSong() = skipToPreviousSongUseCase {

    }

    private fun seekToPosition(position: Long) {
        seekSongToPositionUseCase(position)
    }
}