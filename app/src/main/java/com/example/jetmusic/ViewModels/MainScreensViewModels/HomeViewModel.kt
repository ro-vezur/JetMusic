package com.example.jetmusic.ViewModels.MainScreensViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicResponse
import com.example.jetmusic.data.Remote.Repositories.ApiRepostories.MusicRepository
import com.example.jetmusic.Resources.ResultResource
import com.example.jetmusic.View.Screens.HomeScreen.HomeEvent
import com.example.jetmusic.View.Screens.HomeScreen.HomeUiState
import com.example.jetmusic.domain.usecases.musicController.music.AddMediaItemsUseCase
import com.example.jetmusic.domain.usecases.musicController.music.PauseMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.PlayMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.ResumeMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SkipToNextMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SkipToPreviousMusicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    private val addMediaItemsUseCase: AddMediaItemsUseCase,
    private val playMusicUseCase: PlayMusicUseCase,
    private val pauseMusicUseCase: PauseMusicUseCase,
    private val resumeMusicUseCase: ResumeMusicUseCase,
    private val skipToNextMusicUseCase: SkipToNextMusicUseCase,
    private val skipToPreviousMusicUseCase: SkipToPreviousMusicUseCase,
): ViewModel() {

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    private val _musicOfWeek: MutableStateFlow<ResultResource<MusicResponse>> =
        MutableStateFlow(ResultResource.Loading())
    val musicOfWeek: StateFlow<ResultResource<MusicResponse>> = _musicOfWeek.asStateFlow()

   fun getMusicOfWeek(tags: String, offset: Int, ) = viewModelScope.launch {
       musicRepository.getBestMusicOfWeek(tags, offset).collectLatest { result ->
           _musicOfWeek.emit(result)
       }
   }

    fun setMusicOfWeekLoading() = viewModelScope.launch {
        _musicOfWeek.emit(ResultResource.Loading())
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.AddMediaItems -> {
                addMediaItemsUseCase(musicList = event.musicList)
                homeUiState = homeUiState.copy(musicList = event.musicList)
            }

            HomeEvent.PlaySong -> playMusic()

            HomeEvent.PauseSong -> pauseMusic()

            HomeEvent.ResumeSong -> resumeMusic()

            is HomeEvent.OnSongSelected -> {
                homeUiState = homeUiState.copy(selectedMusic = event.selectedMusic)
            }

            is HomeEvent.SkipToNextSong -> skipToNextSong()

            is HomeEvent.SkipToPreviousSong -> skipToPreviousSong()
        }
    }

    private fun playMusic() {
        homeUiState.apply {
            musicList?.indexOf(selectedMusic)?.let { music ->
                playMusicUseCase(music)
            }
        }
    }

    private fun pauseMusic() {
        pauseMusicUseCase()
    }

    private fun resumeMusic() {
        resumeMusicUseCase()
    }

    private fun skipToNextSong() = skipToNextMusicUseCase {

    }

    private fun skipToPreviousSong() = skipToPreviousMusicUseCase {

    }
}