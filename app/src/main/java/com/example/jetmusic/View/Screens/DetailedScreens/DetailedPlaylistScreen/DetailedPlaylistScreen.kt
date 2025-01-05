package com.example.jetmusic.View.Screens.DetailedScreens.DetailedPlaylistScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.jetmusic.View.ScreenRoutes.ScreensRoutes
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetmusic.Extensions.NavigateExtensions.navigateBack
import com.example.jetmusic.View.Components.Cards.MusicCards.MusicCard
import com.example.jetmusic.View.Components.Slider.MusicPlayerSlider
import com.example.jetmusic.ViewModels.MusicPlayerViewModel
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistObject
import com.example.jetmusic.other.events.MusicPlayerEvent
import com.example.jetmusic.states.PlayerState

@Composable
fun DetailedPlaylistScreen(
    navController: NavController,
    playlistObject: DetailedPlaylistObject,
    musicControllerUiState: MusicControllerUiState,
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
) {

    val isPlaying = musicControllerUiState.playerState == PlayerState.PLAYING

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.32f)
        ){
            AsyncImage(
                model = musicControllerUiState.currentMusic?.image.toString(),
                contentDescription = "playlist image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent, Color.Transparent,
                                colorScheme.background
                            )
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                colorScheme.background.copy(0.3f),
                                Color.Transparent, Color.Transparent,
                            )
                        )
                    )
            )

            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "back",
                tint = colorScheme.inversePrimary,
                modifier = Modifier
                    .padding(start = 12.sdp, top = 22.sdp)
                    .size(28.sdp)
                    .clickable { navController.navigateBack() }
            )

            Text(
                text = playlistObject.name,
                style = typography().headlineMedium,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(bottom = 25.sdp)
                    .align(Alignment.BottomCenter)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 12.sdp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${playlistObject.tracks.size} Songs",
                style = typography().titleSmall,
                fontWeight = FontWeight.W500,
                modifier = Modifier
            )

            Box(
                modifier = Modifier
                    .padding(end = 14.sdp)
                    .size(35.sdp)
                    .clip(CircleShape)
                    .background(colorScheme.inversePrimary)
                    .clickable {
                        musicPlayerViewModel.onEvent(if (isPlaying) MusicPlayerEvent.PauseMusic else MusicPlayerEvent.ResumeMusic)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if(isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = "play/pause",
                    tint = colorScheme.background,
                    modifier = Modifier
                        .size(28.sdp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(top = 6.sdp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.sdp)
        ) {
            item { Spacer(modifier = Modifier.height(16.sdp)) }

            items(playlistObject.tracks) { music ->
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
                                        playlistObject.tracks.indexOf(music)
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
                                    if (musicControllerUiState.currentMusic?.audio?.contains(music.id) == false) {
                                        musicPlayerViewModel.onEvent(
                                            MusicPlayerEvent.SelectMusic(
                                                playlistObject.tracks.indexOf(music)
                                            )
                                        )
                                    }

                                    navController.navigate(ScreensRoutes.DetailedScreens.DetailedMusicRoute)
                                },
                            contentAlignment = Alignment.Center,
                        ){
                            Icon(
                                imageVector = Icons.Filled.ArrowForwardIos,
                                contentDescription = "detailed music",
                                modifier = Modifier
                                    .size(21.sdp)
                            )
                        }
                    },
                    bottomBar = {
                        if(musicControllerUiState.currentMusic?.audio?.contains(music.id) == true) {
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

            item { Spacer(modifier = Modifier.height(4.sdp)) }
        }
    }
}