package com.example.jetmusic.View.Screens.DetailedScreens.DetailedArtistScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
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
import com.example.jetmusic.View.Components.Buttons.TextButton
import com.example.jetmusic.View.Components.Cards.MusicCards.MusicCard
import com.example.jetmusic.View.Components.Slider.MusicPlayerSlider
import com.example.jetmusic.View.ScreensRoutes
import com.example.jetmusic.SharedViewModels.MusicPlayerViewModel
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistObject
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.other.events.MusicPlayerEvent
import com.example.jetmusic.states.PlayerState
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay
import androidx.compose.material3.MaterialTheme.colorScheme
import com.example.jetmusic.Extensions.NavigateExtensions.singleTapNavigate
import com.example.jetmusic.Helpers.MusicHelper

@Composable
fun ArtistMainInfoScreen(
    navController: NavController,
    artistObject: DetailedArtistObject,
    musicControllerUiState: MusicControllerUiState,
    user: User,
    updateUser: (User) -> Unit,
    seeMoreTracks: () -> Unit,
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
) {
    val isPlaying = musicControllerUiState.playerState == PlayerState.PLAYING
    val currentMusicId = MusicHelper.getTrackIdFromUrl(musicControllerUiState.currentMusic?.audio)
    val likedArtistsIds = user.likedArtistsIds

    var showTracks by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(likedArtistsIds.contains(artistObject.id)) }

    LaunchedEffect(null) {
        delay(150)
        showTracks = true
    }

    LazyColumn(
        modifier = Modifier
            .padding(top = 16.sdp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.sdp)
    ) {
        item { Spacer(modifier = Modifier.height(4.sdp)) }

        item {
            Row(
                modifier = Modifier
                    .padding(horizontal = 18.sdp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(
                    text = if(isLiked) "Unfollow" else "Follow",
                    style = typography().bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    background = colorScheme.inversePrimary,
                    height = 36.sdp,
                    width = 80.sdp,
                    onClick = {
                        isLiked = if (isLiked) {
                            likedArtistsIds.remove(artistObject.id)
                            updateUser(user.copy(likedArtistsIds = likedArtistsIds))
                            likedArtistsIds.contains(artistObject.id)
                        } else {
                            likedArtistsIds.add(artistObject.id)
                            updateUser(user.copy(likedArtistsIds = likedArtistsIds))
                            likedArtistsIds.contains(artistObject.id)
                        }
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .padding(end = 14.sdp)
                        .size(35.sdp)
                        .clip(CircleShape)
                        .background(colorScheme.inversePrimary)
                        .clickable {
                            if(isPlaying) {
                                musicPlayerViewModel.onEvent(MusicPlayerEvent.PauseMusic)
                            } else {
                                musicPlayerViewModel.onEvent(MusicPlayerEvent.ResumeMusic)
                            }
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
        }

        item {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.sdp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = "Popular releases",
                    style = typography().titleLarge,
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.weight(1f))

                if(artistObject.tracks.size > 5) {
                    Text(
                        text = "See more",
                        style = typography().bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { seeMoreTracks() }
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(4.sdp)) }

        if(showTracks) {
            itemsIndexed(artistObject.tracks.take(5)) { index,music ->
                MusicCard(
                    modifier = Modifier
                        .padding(horizontal = 10.sdp)
                        .height(42.sdp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.sdp))
                        .clickable {
                            if (currentMusicId != music.id) {
                                musicPlayerViewModel.onEvent(
                                    MusicPlayerEvent.SelectMusic(index)
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
                                    if (MusicHelper.getTrackIdFromUrl(musicControllerUiState.currentMusic?.audio) != music.id) {
                                        musicPlayerViewModel.onEvent(
                                            MusicPlayerEvent.SelectMusic(
                                                artistObject.tracks.indexOf(music)
                                            )
                                        )
                                    }

                                    navController.singleTapNavigate(ScreensRoutes.DetailedScreens.DetailedMusicRoute)
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