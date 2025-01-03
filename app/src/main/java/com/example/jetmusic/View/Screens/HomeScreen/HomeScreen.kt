package com.example.jetmusic.View.Screens.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.Resources.ResultResource
import com.example.jetmusic.View.Components.Cards.MusicCards.MusicCard
import com.example.jetmusic.View.Components.TabsRow.CustomScrollableTabRow
import com.example.jetmusic.View.Screens.HomeScreen.TabsCategories.TabsHomeCategories
import com.example.jetmusic.View.ScreenRoutes.ScreensRoutes
import com.example.jetmusic.ViewModels.MainScreensViewModels.HomeViewModel
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistObject
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.other.events.MusicSelectionEvent
import com.example.jetmusic.ui.theme.darkGrey
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    user: User,
    musicControllerUiState: MusicControllerUiState,
    playlist: DetailedPlaylistObject?,
    setPlaylist: (DetailedPlaylistObject?) -> Unit,
    onEvent: (MusicSelectionEvent) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState { TabsHomeCategories.entries.size }
    val musicOfWeek by homeViewModel.musicOfWeek.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(top = 20.sdp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 12.sdp),
                verticalArrangement = Arrangement.spacedBy(5.sdp)
            ) {
                Text(
                    text = "Hi ${user.fullName},",
                    style = typography().titleSmall,
                    fontWeight = FontWeight.W400,
                    color = Color.LightGray.copy(0.9f)
                )

                Text(
                    text = "Good evening",
                    style = typography().titleMedium
                )
            }
        }

        CustomScrollableTabRow(
            modifier = Modifier
                .padding(top = 20.sdp, start = 6.sdp, end = 6.sdp)
                .fillMaxWidth(),
            pagerState = pagerState,
            items = TabsHomeCategories.entries.map { it.title },
            textColor = Color.Gray,
            containerColor = darkGrey,
            onClick = { index ->
                homeViewModel.setMusicOfWeekLoading()
                homeViewModel.getMusicOfWeek(
                    tags = TabsHomeCategories.entries[index].genreId.toString(),
                    offset = 0
                )
            }
        )

        HorizontalPager(
            userScrollEnabled = false,
            state = pagerState,
            modifier = Modifier
                .padding(top = 18.sdp)
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.78f)
                .shadow(8.sdp, shape = RoundedCornerShape(14.sdp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.DarkGray, colorScheme.background)
                    )
                )
        ) {

                when (musicOfWeek) {
                    is ResultResource.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            CircularProgressIndicator()
                        }
                    }

                    is ResultResource.Success -> {
                        musicOfWeek.data?.let { musicResponse ->

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.sdp)
                            ) {
                                item {  }

                                items(
                                    musicResponse.results,
                                    key = { it.id }
                                ) { music ->
                                    MusicCard(
                                        modifier = Modifier
                                            .padding(start = 10.sdp)
                                            .height(46.sdp)
                                            .fillMaxWidth()
                                            .clickable {
                                                if (musicControllerUiState.currentMusic?.audio != music.audio) {
                                                    val musicList = listOf(music)

                                                    setPlaylist(DetailedPlaylistObject(musicList))
                                                    onEvent(MusicSelectionEvent.SetMediaItem(music))
                                                    onEvent(MusicSelectionEvent.PlaySong(musicList, music))
                                                }

                                                navController.navigate(ScreensRoutes.DetailedScreens.DetailedMusicRoute)
                                            },
                                        musicObject = music
                                    )
                                }
                            }
                        }
                    }

                    is ResultResource.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "error")
                        }
                    }
                }
            }

    }
}

