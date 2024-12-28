package com.example.jetmusic.ViewModels.DetailedScreensViewModels

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicDetailedViewModel @Inject constructor(

): ViewModel() {

    val mediaPlayer = MediaPlayer()

    private val _oldAudioUrl: MutableStateFlow<String> = MutableStateFlow("")
    val oldAudioUrl: StateFlow<String> = _oldAudioUrl.asStateFlow()

    private val _currentAudioId: MutableStateFlow<String> = MutableStateFlow("")
    val currentAudioId: StateFlow<String> = _currentAudioId.asStateFlow()

    private val _isPlaying: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentPosition: StateFlow<Int> = _currentPosition.asStateFlow()

    fun setPlayStatus(status: Boolean) = viewModelScope.launch {
        _isPlaying.emit(status)
    }

    fun setCurrentPosition(position: Int) = viewModelScope.launch {
        _currentPosition.emit(position)
    }

    fun setOldAudioUrl(url: String) = viewModelScope.launch {
        _oldAudioUrl.emit(url)
    }

    fun setCurrentAudioId(url: String) = viewModelScope.launch {
        _currentAudioId.emit(url)
    }

}