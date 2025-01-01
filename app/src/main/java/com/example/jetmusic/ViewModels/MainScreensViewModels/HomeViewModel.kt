package com.example.jetmusic.ViewModels.MainScreensViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicResponse
import com.example.jetmusic.data.Remote.Repositories.ApiRepostories.MusicRepository
import com.example.jetmusic.Resources.ResultResource
import com.example.jetmusic.other.events.MusicSelectionEvent
import com.example.jetmusic.domain.usecases.musicController.music.AddMediaItemsUseCase
import com.example.jetmusic.domain.usecases.musicController.music.PauseMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.PlayMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.ResumeMusicUseCase
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
): ViewModel() {

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

    fun onEvent(event: MusicSelectionEvent) {
        when (event) {
            is MusicSelectionEvent.AddMediaItems -> addMediaItemsUseCase(musicList = event.musicList)

            is MusicSelectionEvent.PlaySong ->  playMusicUseCase(event.musicList.indexOf(event.selectedMusic))

            MusicSelectionEvent.PauseSong -> pauseMusicUseCase()

            MusicSelectionEvent.ResumeSong -> resumeMusicUseCase()
        }
    }
}