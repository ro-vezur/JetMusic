package com.example.jetmusic.View.Screens.DetailedScreens.DetailedArtistScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetmusic.View.Components.Cards.MusicCards.MusicCard
import com.example.jetmusic.View.Components.Slider.MusicPlayerSlider
import com.example.jetmusic.View.ScreenRoutes.ScreensRoutes
import com.example.jetmusic.ViewModels.MusicPlayerViewModel
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistObject
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.other.events.MusicPlayerEvent
import com.example.jetmusic.states.PlayerState
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay

@Composable
fun MoreArtistMusicsScreen(
    navController: NavController,
    artistObject: DetailedArtistObject,
    musicControllerUiState: MusicControllerUiState,
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
) {
    val isPlaying = musicControllerUiState.playerState == PlayerState.PLAYING

    var showTracks by remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        delay(200)
        showTracks = true
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.sdp)
            .fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .padding(top = 12.sdp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${artistObject.tracks.size} Songs",
                style = typography().titleLarge,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .padding(end = 14.sdp)
                    .size(35.sdp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .clickable {
                        musicPlayerViewModel.onEvent(if (isPlaying) MusicPlayerEvent.PauseMusic else MusicPlayerEvent.ResumeMusic)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if(isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = "play/pause",
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .size(28.sdp)
                )
            }
        }

        if(showTracks) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 12.sdp),
                verticalArrangement = Arrangement.spacedBy(8.sdp)
            ) {
                item { Spacer(modifier = Modifier.height(4.sdp)) }

                items(artistObject.tracks) { music ->
                    MusicCard(
                        modifier = Modifier
                            .padding(horizontal = 10.sdp)
                            .height(42.sdp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.sdp))
                            .clickable {
                                if (musicControllerUiState.currentMusic?.audio?.contains(music.id) == false) {
                                    musicPlayerViewModel.onEvent(
                                        MusicPlayerEvent.SelectMusic(
                                            artistObject.tracks.indexOf(music)
                                        )
                                    )
                                }
                            },
                        musicObject = music,
                        trailingIcon = {
                            Box(
                                modifier = Modifier
                                    .padding(end = 4.sdp)
                                    .size(32.sdp)
                                    .clip(RoundedCornerShape(10.sdp))
                                    .clickable {
                                        if (musicControllerUiState.currentMusic?.audio?.contains(
                                                music.id
                                            ) == false
                                        ) {
                                            musicPlayerViewModel.onEvent(
                                                MusicPlayerEvent.SelectMusic(
                                                    artistObject.tracks.indexOf(music)
                                                )
                                            )
                                        }

                                        navController.navigate(ScreensRoutes.DetailedScreens.DetailedMusicRoute)
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowForwardIos,
                                    contentDescription = "detailed music",
                                    modifier = Modifier
                                        .size(21.sdp)
                                )
                            }
                        },
                        bottomBar = {
                            if (musicControllerUiState.currentMusic?.audio?.contains(music.id) == true) {
                                MusicPlayerSlider(
                                    modifier = Modifier
                                        .padding(start = 8.sdp, end = 8.sdp, top = 8.sdp)
                                        .height(4.sdp),
                                    musicControllerUiState = musicControllerUiState,
                                    onEvent = musicPlayerViewModel::onEvent
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}