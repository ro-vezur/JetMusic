package com.example.jetmusic.View.Components.Slider

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.jetmusic.other.events.MusicPlayerEvent
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.states.PlayerState
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerSlider(
    modifier: Modifier = Modifier,
    musicControllerUiState: MusicControllerUiState,
    onEvent: (MusicPlayerEvent) -> Unit,
    onScrollAdditional: () -> Unit = {},
    onReleaseAdditional: () -> Unit = {},
) {
    var positionUpdatesCounter by remember { mutableStateOf(0) }

    var position by remember {
        mutableStateOf(musicControllerUiState.currentPosition.toFloat())
    }
    val totalDuration by remember(musicControllerUiState.totalDuration) {
       mutableStateOf(musicControllerUiState.totalDuration.toFloat())
    }

    var wasPlayingBefore by remember {
        mutableStateOf(musicControllerUiState.playerState == PlayerState.PLAYING)
    }

    LaunchedEffect(musicControllerUiState.currentPosition) {
        if(positionUpdatesCounter <= 0) {
            position = musicControllerUiState.currentPosition.toFloat()
        }
    }

    Slider(
        value = position,
        modifier = modifier,
        valueRange = 0f..totalDuration,
        onValueChange = { newPosition ->
            if(positionUpdatesCounter == 0) {
                wasPlayingBefore = musicControllerUiState.playerState == PlayerState.PLAYING

            }

            if(musicControllerUiState.playerState == PlayerState.PLAYING) {
                onEvent(MusicPlayerEvent.PauseMusic)
            }

            position = newPosition
            Log.d("new position",newPosition.toString())

            if(positionUpdatesCounter <= 0) {
                positionUpdatesCounter++
            }

        },
        onValueChangeFinished = {
            onEvent(MusicPlayerEvent.SeekSongToPosition(position.toLong()))
            if(wasPlayingBefore) {
                onEvent(MusicPlayerEvent.ResumeMusic)
            }

            positionUpdatesCounter = 0
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
                        .fillMaxWidth(position / totalDuration)
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