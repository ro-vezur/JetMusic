package com.example.jetmusic.View.Components.BottomPlayer

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject

data class MusicPlayerUiState(
    val currentMusic: MusicObject? = null,
    val playList: List<MusicObject>? = null,
)
