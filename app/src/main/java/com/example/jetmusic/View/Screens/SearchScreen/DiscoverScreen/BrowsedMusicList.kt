package com.example.jetmusic.View.Screens.SearchScreen.DiscoverScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.jetmusic.BOTTOM_MUSIC_PLAYER_HEIGHT
import com.example.jetmusic.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetmusic.Extensions.NavigateExtensions.navigateBack
import com.example.jetmusic.View.Components.Cards.MusicCards.MusicCard
import com.example.jetmusic.View.Components.Cards.UnifiedDataCards.UnifiedDataCard
import com.example.jetmusic.View.Components.TopBars.TopBarWithNavigateBack
import com.example.jetmusic.View.Screens.ResultScreens.ErrorScreen
import com.example.jetmusic.View.Screens.ResultScreens.LoadingScreen
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.data.DTOs.API.UnifiedData.MediaTypes
import ir.kaaveh.sdpcompose.sdp

@Composable
fun BrowsedMusicList(
    modifier: Modifier = Modifier,
    currentMusic: MusicObject?,
    genre: String,
    paginatedSongs: LazyPagingItems<MusicObject>,
    turnBack: () -> Unit,
    navigateToSelectedMusic: (MusicObject) -> Unit,
) {
    BackHandler {
        turnBack()
    }

    Scaffold(
        topBar = {
            TopBarWithNavigateBack(
                title = "$genre Songs",
                turnBack = turnBack
            )
        }
    ) { innerPadding ->
        when (paginatedSongs.loadState.refresh) {
            is LoadState.Loading -> { LoadingScreen(Modifier.fillMaxSize())
            }
            is LoadState.NotLoading -> {
                LazyColumn(
                    modifier,
                    verticalArrangement = Arrangement.spacedBy(14.sdp),
                ) {
                    item {
                        Spacer(modifier = Modifier.height(innerPadding.calculateTopPadding()))
                    }

                    items(
                        paginatedSongs.itemCount,
                        key = {it}
                    ) { index ->
                        paginatedSongs[index]?.let { musicObject ->
                            MusicCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.sdp)
                                    .clip(RoundedCornerShape(8.sdp))
                                    .clickable {
                                        navigateToSelectedMusic(musicObject)
                                    },
                                musicObject = musicObject
                            )
                        }
                    }

                    item {
                        if(currentMusic == null) {
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

            is LoadState.Error -> {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    errorText = (paginatedSongs.loadState.refresh as LoadState.Error).error.message.toString()
                )
            }
        }
    }
}