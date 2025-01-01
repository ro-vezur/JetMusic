package com.example.jetmusic.View.Components.Slider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.jetmusic.other.events.MusicPlayerEvent
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerSlider(
    modifier: Modifier = Modifier,
    musicControllerUiState: MusicControllerUiState,
    onEvent: (MusicPlayerEvent) -> Unit,
) {
    Slider(
        value = musicControllerUiState.currentPosition.toFloat(),
        modifier = modifier,
        valueRange = 0f..musicControllerUiState.totalDuration.toFloat(),
        onValueChange = { newPosition ->
            onEvent(MusicPlayerEvent.SeekSongToPosition(newPosition.toLong()))
        },
        track = { sliderState ->
            Box(modifier = Modifier.fillMaxWidth()){
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(2.sdp)
                        .background(Color.Gray, CircleShape)
                )

                Box(
                    Modifier
                        .fillMaxWidth(musicControllerUiState.currentPosition.toFloat() / musicControllerUiState.totalDuration.toFloat())
                        .height(2.sdp)
                        .background(Color.White, CircleShape)
                )
            }
        },
        thumb = { position ->
            Box(
                modifier = Modifier
                    .height(15.sdp)
                    .width(15.sdp),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Box(
                    modifier = Modifier
                        .size(11.sdp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.sdp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
            }
        }
    )
}