package com.example.jetmusic.View.Screens.HomeScreen

import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject

data class HomeUiState(
    val loading: Boolean? = false,
    val musicList: List<MusicObject>? = emptyList(),
    val selectedMusic: MusicObject? = null,
    val errorMessage: String? = null
)