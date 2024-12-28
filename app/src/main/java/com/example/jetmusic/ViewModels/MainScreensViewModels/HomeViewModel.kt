package com.example.jetmusic.ViewModels.MainScreensViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicResponse
import com.example.jetmusic.data.Remote.Repositories.ApiRepostories.MusicRepository
import com.example.jetmusic.Resources.ResultResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val musicRepository: MusicRepository
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
}