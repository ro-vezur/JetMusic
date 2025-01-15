package com.example.jetmusic.View.Screens.MusicLibraryScreen.Screens

import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistObject
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.jetmusic.BOTTOM_MUSIC_PLAYER_HEIGHT
import com.example.jetmusic.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetmusic.Extensions.NavigateExtensions.navigateBack
import com.example.jetmusic.View.Components.Cards.ArtistCards.ArtistCard
import com.example.jetmusic.View.Components.InputFields.SearchField
import com.example.jetmusic.View.Components.TopBars.TopBarWithNavigateBack
import com.example.jetmusic.View.Screens.ResultScreens.ErrorScreen
import com.example.jetmusic.View.Screens.ResultScreens.LoadingScreen
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.other.Resources.ResultResource
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun LikedArtistsScreen(
    navController: NavController,
    currentMusicObject: MusicObject?,
    likedArtistsCount: Int,
    likedArtistsResult: ResultResource<List<DetailedArtistObject>>,
    selectArtist: (DetailedArtistObject) -> Unit,
) {
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBarWithNavigateBack(
                title = "Liked Artists",
                turnBack = { navController.navigateBack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when(likedArtistsResult) {
                is ResultResource.Loading -> { LoadingScreen(Modifier.fillMaxSize()) }
                is ResultResource.Success -> {
                    likedArtistsResult.data?.let { data ->
                        val sortedData = remember(data,searchText) {
                            data.filter { it.name.contains(searchText,true) }
                        }

                        Text(
                            modifier = Modifier
                                .padding(start = 16.sdp,top = 4.sdp),
                            text = "$likedArtistsCount Artists",
                            style = typography().titleSmall,
                            color = Color.Gray
                        )

                        Box(
                            modifier = Modifier
                                .padding(top = 12.sdp, bottom = 10.sdp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            SearchField(
                                text = searchText,
                                background = MaterialTheme.colorScheme.inversePrimary,
                                textColor = MaterialTheme.colorScheme.background,
                                focusedBorder = BorderStroke(0.sdp, Color.Transparent),
                                unfocusedBorder = BorderStroke(0.sdp, Color.Transparent),
                                onTextChange = { value ->
                                    searchText = value
                                },
                                onSearchClick = {

                                },
                                searchIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "search",
                                        tint = MaterialTheme.colorScheme.background,
                                        modifier = Modifier
                                            .padding(start = 12.sdp)
                                            .size(21.sdp)
                                            .clip(RoundedCornerShape(8.sdp))
                                    )
                                }
                            )
                        }

                        LazyVerticalGrid(
                            modifier = Modifier
                                .padding(horizontal = 12.sdp),
                            columns = GridCells.Fixed(3),
                            horizontalArrangement = Arrangement.spacedBy(14.sdp),
                            verticalArrangement = Arrangement.spacedBy(14.sdp),
                        ) {
                            items(sortedData) { artist ->
                                ArtistCard(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectArtist(artist) },
                                    artistImage = artist.image,
                                    artistName = artist.name,
                                    textStyle = typography().bodyMedium
                                )
                            }

                            item(span = { GridItemSpan(maxLineSpan) } ) {
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
                }
                is ResultResource.Error -> {
                    ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        errorText = likedArtistsResult.message.toString()
                    )
                }
            }
        }
    }
}