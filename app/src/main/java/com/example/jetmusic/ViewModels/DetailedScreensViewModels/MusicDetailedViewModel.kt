package com.example.jetmusic.ViewModels.DetailedScreensViewModels

import android.graphics.Bitmap
import android.media.MediaPlayer
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.jetmusic.View.Screens.DetailedScreens.DetailedMusicScreen.MusicEvent
import com.example.jetmusic.domain.usecases.musicController.music.PauseMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.ResumeMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SeekMusicToPositionUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SkipToNextMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SkipToPreviousMusicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicDetailedViewModel @Inject constructor(
    private val pauseSongUseCase: PauseMusicUseCase,
    private val resumeSongUseCase: ResumeMusicUseCase,
    private val skipToNextSongUseCase: SkipToNextMusicUseCase,
    private val skipToPreviousSongUseCase: SkipToPreviousMusicUseCase,
    private val seekSongToPositionUseCase: SeekMusicToPositionUseCase,
) : ViewModel() {
    fun onEvent(event: MusicEvent) {
        when (event) {
            MusicEvent.PauseSong -> pauseMusic()
            MusicEvent.ResumeSong -> resumeMusic()
            is MusicEvent.SeekSongToPosition -> seekToPosition(event.position)
            MusicEvent.SkipToNextSong -> skipToNextSong()
            MusicEvent.SkipToPreviousSong -> skipToPreviousSong()
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