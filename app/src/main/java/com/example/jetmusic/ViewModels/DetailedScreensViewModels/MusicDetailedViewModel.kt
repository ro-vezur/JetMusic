package com.example.jetmusic.ViewModels.DetailedScreensViewModels

import androidx.lifecycle.ViewModel
import com.example.jetmusic.other.events.MusicPlayerEvent
import com.example.jetmusic.domain.usecases.musicController.music.PauseMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.ResumeMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SeekMusicToPositionUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SkipToNextMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SkipToPreviousMusicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicDetailedViewModel @Inject constructor(
    private val pauseSongUseCase: PauseMusicUseCase,
    private val resumeSongUseCase: ResumeMusicUseCase,
    private val skipToNextSongUseCase: SkipToNextMusicUseCase,
    private val skipToPreviousSongUseCase: SkipToPreviousMusicUseCase,
    private val seekSongToPositionUseCase: SeekMusicToPositionUseCase,
) : ViewModel() {
    fun onEvent(event: MusicPlayerEvent) {
        when (event) {
            MusicPlayerEvent.PauseSong -> pauseMusic()
            MusicPlayerEvent.ResumeSong -> resumeMusic()
            is MusicPlayerEvent.SeekSongToPosition -> seekToPosition(event.position)
            MusicPlayerEvent.SkipToNextSong -> skipToNextSong()
            MusicPlayerEvent.SkipToPreviousSong -> skipToPreviousSong()
        }
    }

    private fun pauseMusic() {
        pauseSongUseCase()
    }

    private fun resumeMusic() {
        resumeSongUseCase()
    }

    private fun skipToNextSong() = skipToNextSongUseCase {

    }

    private fun skipToPreviousSong() = skipToPreviousSongUseCase {

    }

    private fun seekToPosition(position: Long) {
        seekSongToPositionUseCase(position)
    }

}