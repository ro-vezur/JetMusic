package com.example.jetmusic.View.Screens.SearchScreen

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetmusic.ViewModels.MainScreensViewModels.SearchViewModel
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.jetmusic.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetmusic.DTOs.Genres.MusicGenres
import com.example.jetmusic.Resources.ResultResource
import com.example.jetmusic.View.Components.Cards.ArtistCard
import com.example.jetmusic.View.Components.Cards.MusicCards.MusicGenreCard
import com.example.jetmusic.View.Components.InputFields.SearchField

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
) {

    val trendingArtistsResult by searchViewModel.trendingArtists.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp

    var showSearchHistoryScreen by remember { mutableStateOf(false) }

    val searchBarPrimaryColor by animateColorAsState(targetValue = if (showSearchHistoryScreen)
        colorScheme.background else colorScheme.inversePrimary
    )

    val searchBarInversePrimaryColor by animateColorAsState(targetValue = if (showSearchHistoryScreen)
        colorScheme.inversePrimary else colorScheme.background
    )

    var searchText by remember { mutableStateOf("") }

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.sdp),
                contentAlignment = Alignment.Center
            ){
                SearchField(
                    modifier = Modifier
                        .clickable {
                            showSearchHistoryScreen = true
                        },
                    text = searchText,
                    onTextChange = { value -> searchText = value },
                    primaryColor = searchBarPrimaryColor,
                    inversePrimaryColor = searchBarInversePrimaryColor,
                    focusedBorder = BorderStroke(0.sdp, Color.Transparent),
                    unfocusedBorder = BorderStroke(0.sdp,Color.Transparent),
                    shape = RoundedCornerShape(8.sdp),
                    onSearchClick = {

                    },
                    onCancelClick = {

                    },
                    searchIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "search",
                            tint = searchBarInversePrimaryColor,
                            modifier = Modifier
                                .padding(start = 12.sdp)
                                .size(24.sdp)
                                .clip(RoundedCornerShape(8.sdp))
                                .clickable {
                                    focusManager.clearFocus()
                                }
                        )
                    },
                    leadingIcon = if(showSearchHistoryScreen) {
                        {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "back",
                                tint = searchBarInversePrimaryColor,
                                modifier = Modifier
                                    .padding(start = 12.sdp)
                                    .size(24.sdp)
                                    .clip(RoundedCornerShape(8.sdp))
                                    .clickable {
                                        focusManager.clearFocus()
                                    }
                            )
                        }
                    }
                     else null,
                    trailingIcon = {

                    }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {

            item {
                Column{
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
                            Box(
                                modifier = Modifier
                                    .padding(top = 15.sdp)
                                    .fillMaxWidth()
                                    .height(85.sdp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
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
                                        if(artist.name.isNotBlank() && artist.image.isNotBlank()) {
                                            ArtistCard(
                                                modifier = Modifier
                                                    .height(85.sdp)
                                                    .width(50.sdp),
                                                artistObject = artist
                                            )
                                        }
                                    }
                                }

                                item { }
                            }
                        }

                        is ResultResource.Error -> {

                        }
                    }
                }
            }
            
            item {
                Text(
                    modifier = Modifier
                        .padding(start = 14.sdp, bottom = 13.sdp,top = 10.sdp),
                    text = "Browse",
                    style = typography().titleMedium,
                )
            }

            items(
                MusicGenres.entries.toList().chunked(2),
                key = { it}
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
                                .clip(RoundedCornerShape(14.sdp)),
                            musicGenre = musicGenre
                        )
                    }
                }
            }

            item{
                Spacer(
                    modifier = Modifier.height((BOTTOM_NAVIGATION_BAR_HEIGHT + 15).sdp)
                )
            }
        }
    }
}
