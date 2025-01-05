package com.example.jetmusic.ViewModels.MainScreensViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicResponse
import com.example.jetmusic.other.Resources.ResultResource
import com.example.jetmusic.domain.usecases.api.musicAPI.music.BestMusicOfWeekUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBestMusicOfWeekUseCase: BestMusicOfWeekUseCase,
): ViewModel() {

    private val _musicOfWeek: MutableStateFlow<ResultResource<MusicResponse>> =
        MutableStateFlow(ResultResource.Loading())
    val musicOfWeek: StateFlow<ResultResource<MusicResponse>> = _musicOfWeek.asStateFlow()

   fun getMusicOfWeek(tags: String, offset: Int, ) = viewModelScope.launch {
       flow {
           emit(ResultResource.Loading())
           val response = getBestMusicOfWeekUseCase(tags,offset)
           if(response.results.isNotEmpty()) {
               emit(ResultResource.Success(data = response))
           } else {
               emit(ResultResource.Error(message = "empty result"))
           }
       }.catch { e ->
           emit(ResultResource.Error(message = e.message.toString()))
       }.collectLatest { result ->
           _musicOfWeek.emit(result)
       }
   }

    fun setMusicOfWeekLoading() = viewModelScope.launch {
        _musicOfWeek.emit(ResultResource.Loading())
    }
}