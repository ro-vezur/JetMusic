package com.example.jetmusic.View.Components.BottomBar.MusicBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.jetmusic.View.Components.Slider.MusicPlayerSlider
import com.example.jetmusic.other.events.MusicPlayerEvent
import com.example.jetmusic.ViewModels.DetailedScreensViewModels.MusicDetailedViewModel
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.states.PlayerState
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicBottomBar(
    modifier: Modifier = Modifier,
    musicControllerUiState: MusicControllerUiState,
    musicDetailedViewModel: MusicDetailedViewModel = hiltViewModel(),
) {
    val imageSize = 38.sdp

    musicControllerUiState.currentMusic?.let { musicObject ->

        val isPlaying = musicControllerUiState.playerState == PlayerState.PLAYING

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 11.sdp, vertical = 5.sdp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = musicObject.image,
                    contentDescription = "music image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(imageSize)
                        .clip(RoundedCornerShape(8.sdp))
                )

                Column(
                    modifier = Modifier
                        .padding(top = 1.sdp, start = 11.sdp)
                        .height(imageSize),
                    verticalArrangement = Arrangement.spacedBy(2.sdp)
                ) {
                    Text(
                        text = musicObject.name,
                        fontSize = typography().bodyMedium.fontSize * 1.05f,
                    )

                    Text(
                        text = musicObject.artist_name,
                        fontSize = typography().bodyMedium.fontSize,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    modifier = Modifier
                        .padding(end = 22.sdp)
                        .size(27.sdp)
                        .clip(RoundedCornerShape(8.sdp))
                        .clickable {
                            musicDetailedViewModel.onEvent(
                                if (isPlaying) MusicPlayerEvent.PauseSong
                                else MusicPlayerEvent.ResumeSong
                            )
                        },
                    imageVector = if(isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = "play/pause icon",
                    tint = Color.White,
                )
            }

            MusicPlayerSlider(
                modifier = Modifier
                    .padding(horizontal = 10.sdp)
                    .height(20.sdp)
                    .fillMaxWidth(),
                musicControllerUiState = musicControllerUiState,
                onEvent = musicDetailedViewModel::onEvent
            )
        }
    }
}