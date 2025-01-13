package com.example.jetmusic.View.Screens.MusicLibraryScreen.Screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.jetmusic.BOTTOM_MUSIC_PLAYER_HEIGHT
import com.example.jetmusic.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetmusic.Extensions.NavigateExtensions.navigateBack
import com.example.jetmusic.View.Components.Cards.MusicCards.MusicCard
import com.example.jetmusic.View.Components.InputFields.SearchField
import com.example.jetmusic.View.Components.TopBars.TopBarWithNavigateBack
import com.example.jetmusic.View.Screens.ResultScreens.ErrorScreen
import com.example.jetmusic.View.Screens.ResultScreens.LoadingScreen
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.other.Resources.ResultResource
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun LikedSongsScreen(
    navController: NavController,
    currentMusicObject: MusicObject?,
    likedSongsCount: Int,
    likedMusicResult: ResultResource<List<MusicObject>>,
    selectMusic: (MusicObject) -> Unit,
) {

    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBarWithNavigateBack(
                title = "Liked Songs",
                turnBack = { navController.navigateBack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {

            when(likedMusicResult){
                is ResultResource.Loading -> { LoadingScreen(Modifier.fillMaxSize()) }
                is ResultResource.Success -> {
                    likedMusicResult.data?.let { data ->
                        val sortedData = remember(data,searchText) {
                            data.filter { it.name.contains(searchText,true) }
                        }
                        Text(
                            modifier = Modifier
                                .padding(start = 16.sdp,top = 4.sdp),
                            text = "$likedSongsCount Songs",
                            style = typography().titleSmall,
                            color = Color.Gray
                        )

                        Box(
                            modifier = Modifier
                                .padding(top = 12.sdp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            SearchField(
                                text = searchText,
                                background = MaterialTheme.colorScheme.inversePrimary,
                                textColor = MaterialTheme.colorScheme.background,
                                focusedBorder = BorderStroke(0.sdp, Color.Transparent),
                                unfocusedBorder = BorderStroke(0.sdp,Color.Transparent),
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

                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 10.sdp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.sdp)
                        ) {
                            items(sortedData) { music ->
                                MusicCard(
                                    modifier = Modifier
                                        .padding(start = 10.sdp)
                                        .height(42.sdp)
                                        .fillMaxWidth()
                                        .clickable { selectMusic(music) },
                                    musicObject = music
                                )
                            }

                            item {
                                if (currentMusicObject == null) {
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
                        errorText = likedMusicResult.message.toString()
                    )
                }
            }
        }
    }
}