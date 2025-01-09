package com.example.jetmusic.View.Screens.DetailedScreens.DetailedMusicScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow
import com.example.jetmusic.Helpers.MusicHelper
import com.example.jetmusic.Helpers.TimeHelper
import com.example.jetmusic.View.Components.Slider.MusicPlayerSlider
import com.example.jetmusic.ViewModels.LikedSongsViewModel
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.other.events.MusicPlayerEvent
import com.example.jetmusic.states.PlayerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicDetailedScreen(
    musicControllerUiState: MusicControllerUiState,
    user: User,
    navigateBack: () -> Unit,
    updateUser: (User) -> Unit,
    onEvent: (MusicPlayerEvent) -> Unit,
    removeMusicFromLikedList: (String) -> Unit,
) {

    musicControllerUiState.currentMusic?.let { musicObject ->
        val likedSongs = user.likedSongsIds
        val isPlaying = musicControllerUiState.playerState == PlayerState.PLAYING
        val musicId = MusicHelper.getTrackIdFromUrl(musicObject.audio)

        var isLiked by remember{ mutableStateOf(likedSongs.contains(musicId)) }
        val isDownloaded = false

        Column(
            modifier = Modifier
                .padding(horizontal = 20.sdp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 20.sdp)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "arrow down",
                    modifier = Modifier
                        .size(42.sdp)
                        .clip(RoundedCornerShape(10.sdp))
                        .clickable { navigateBack() }
                )
            }

            AsyncImage(
                model = musicObject.image,
                contentDescription = "music image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 24.sdp)
                    .size(250.sdp)
                    .clip(RoundedCornerShape(15.sdp))
            )

            Row(
                modifier = Modifier
                    .padding(top = 16.sdp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                Column(
                    modifier = Modifier
                        .width(180.sdp)
                ) {
                    Text(
                        text = musicObject.name,
                        style = typography().titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .height(20.sdp)
                    )

                    if(musicObject.artist_name != "null" && !musicObject.artist_name.isNullOrBlank()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 6.sdp)
                                .height(20.sdp),
                            text = musicObject.artist_name,
                            style = typography().titleSmall,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                    }
                }

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(10.sdp)
                ) {
                    Icon(
                        imageVector = if(isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "like",
                        modifier = Modifier
                            .size(30.sdp)
                            .clip(RoundedCornerShape(6.sdp))
                            .clickable {
                                isLiked = if (isLiked) {
                                    likedSongs.remove(musicId)
                                    updateUser(user.copy(likedSongsIds = likedSongs))
                                    removeMusicFromLikedList(musicId)
                                    likedSongs.contains(musicId)
                                } else {
                                    likedSongs.add(musicId)
                                    updateUser(user.copy(likedSongsIds = likedSongs))
                                    likedSongs.contains(musicId)
                                }
                            }
                    )

                    Icon(
                        imageVector = Icons.Outlined.Download,
                        contentDescription = "like",
                        modifier = Modifier
                            .size(30.sdp)
                            .clip(RoundedCornerShape(6.sdp))
                            .clickable {

                            }
                    )
                }
            }

            MusicPlayerSlider(
                modifier = Modifier
                    .padding(top = 14.sdp)
                    .height(20.sdp)
                    .fillMaxWidth(),
                musicControllerUiState = musicControllerUiState,
                onEvent = onEvent
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 5.sdp, vertical = 2.sdp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = TimeHelper.formatTimeFromMillis(musicControllerUiState.currentPosition),
                    style = typography().bodyMedium,
                    color = Color.Gray
                )

                Text(
                    text = TimeHelper.formatTimeFromMillis(musicControllerUiState.totalDuration),
                    style = typography().bodyMedium,
                    color = Color.Gray
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 10.sdp),
                horizontalArrangement = Arrangement.spacedBy(12.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.SkipPrevious,
                    contentDescription = "skip previous",
                    tint = colorScheme.inversePrimary,
                    modifier = Modifier
                        .size(33.sdp)
                        .clip(RoundedCornerShape(8.sdp))
                        .clickable {
                            onEvent(MusicPlayerEvent.SkipToPreviousMusic)
                        }
                )

                Box(
                    modifier = Modifier
                        .size(46.sdp)
                        .clip(CircleShape)
                        .background(colorScheme.inversePrimary)
                        .clickable {
                            if (isPlaying) {
                                onEvent(MusicPlayerEvent.PauseMusic)
                            } else {
                                onEvent(MusicPlayerEvent.ResumeMusic)
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = "play/pause",
                        tint = colorScheme.background,
                        modifier = Modifier
                            .size(33.sdp)
                    )
                }

                Icon(
                    imageVector = Icons.Filled.SkipNext,
                    contentDescription = "skip next",
                    tint = colorScheme.inversePrimary,
                    modifier = Modifier
                        .size(33.sdp)
                        .clip(RoundedCornerShape(8.sdp))
                        .clickable {
                            onEvent(MusicPlayerEvent.SkipToNextMusic)
                        }
                )
            }
        }
    }
}