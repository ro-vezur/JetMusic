package com.example.jetmusic.View.Screens.SearchScreen.DiscoverScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetmusic.BOTTOM_MUSIC_PLAYER_HEIGHT
import com.example.jetmusic.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Simplified.SimplifiedArtistResponse
import com.example.jetmusic.data.enums.Genres.MusicGenres
import com.example.jetmusic.other.Resources.ResultResource
import com.example.jetmusic.View.Components.Cards.ArtistCards.ArtistCard
import com.example.jetmusic.View.Components.Cards.MusicCards.MusicGenreCard
import com.example.jetmusic.View.ScreenRoutes.ScreensRoutes
import com.example.jetmusic.View.Screens.ResultScreens.ErrorScreen
import com.example.jetmusic.View.Screens.ResultScreens.LoadingScreen
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    trendingArtistsResult: ResultResource<SimplifiedArtistResponse>,
    currentMusicObject: MusicObject?,
    selectArtist: (String) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    LazyColumn(
        modifier = modifier
    ) {
        item {
            Column {
                Row(
                    modifier = Modifier
                        .padding(start = 14.sdp, top = 12.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Filled.TrendingUp,
                        contentDescription = "trending up",
                        modifier = Modifier
                            .size(30.sdp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 11.sdp),
                        text = "Trending Artists",
                        style = typography().titleMedium,
                    )
                }

                when (trendingArtistsResult) {
                    is ResultResource.Loading -> {
                        LoadingScreen(
                            modifier = Modifier
                                .padding(top = 15.sdp)
                                .fillMaxWidth()
                                .height(85.sdp)
                        )
                    }

                    is ResultResource.Success -> {
                        LazyRow(
                            modifier = Modifier
                                .padding(top = 15.sdp)
                                .height(85.sdp),
                            horizontalArrangement = Arrangement.spacedBy(9.sdp)
                        ) {
                            item { }

                            trendingArtistsResult.data?.let { artists ->
                                items(artists.results) { artist ->
                                    if (artist.name.isNotBlank() && artist.image.isNotBlank()) {
                                        ArtistCard(
                                            modifier = Modifier
                                                .height(85.sdp)
                                                .width(50.sdp)
                                                .clip(RoundedCornerShape(8.sdp))
                                                .clickable { selectArtist(artist.id) },
                                            artistImage = artist.image,
                                            artistName = artist.name,
                                        )
                                    }
                                }
                            }

                            item { }
                        }
                    }

                    is ResultResource.Error -> {
                        ErrorScreen(
                            modifier = Modifier
                                .padding(top = 15.sdp)
                                .fillMaxWidth()
                                .height(85.sdp),
                            errorText = trendingArtistsResult.message.toString()
                        )
                    }
                }
            }
        }

        item {
            Text(
                modifier = Modifier
                    .padding(start = 14.sdp, bottom = 13.sdp, top = 10.sdp),
                text = "Browse",
                style = typography().titleMedium,
            )
        }

        items(
            MusicGenres.entries.toList().chunked(2),
            key = { it }
        ) { musicGenresChunk ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 7.sdp, vertical = 5.sdp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                musicGenresChunk.forEach { musicGenre ->
                    MusicGenreCard(
                        modifier = Modifier
                            .padding(horizontal = 5.sdp)
                            .width(screenWidth.dp / 2.32f)
                            .height(88.sdp)
                            .clip(RoundedCornerShape(14.sdp))
                            .clickable {
                                navController.navigate(
                                    ScreensRoutes.MainSearchRoute.BrowsedMusicListRoute(musicGenre)
                                )
                            },
                        musicGenre = musicGenre
                    )
                }
            }
        }

        item {
            if(currentMusicObject == null) {
                Spacer(modifier = Modifier.height((BOTTOM_NAVIGATION_BAR_HEIGHT + 15).sdp))
            } else {
                Spacer(
                    modifier = Modifier
                        .height((BOTTOM_NAVIGATION_BAR_HEIGHT + BOTTOM_MUSIC_PLAYER_HEIGHT + 15).sdp)
                )
            }
        }
    }
}