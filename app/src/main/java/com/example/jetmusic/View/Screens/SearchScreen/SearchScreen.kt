package com.example.jetmusic.View.Screens.SearchScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetmusic.ViewModels.MainScreensViewModels.SearchViewModel
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetmusic.View.Components.Buttons.TextButton
import com.example.jetmusic.View.Components.InputFields.SearchField
import com.example.jetmusic.View.ScreenRoutes.ScreensRoutes
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistObject
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.other.events.MusicSelectionEvent
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun SearchScreen(
    navController: NavController,
    musicControllerUiState: MusicControllerUiState,
    playlist: DetailedPlaylistObject?,
    setPlaylist: (DetailedPlaylistObject?) -> Unit,
    onEvent: (MusicSelectionEvent) -> Unit,
    searchViewModel: SearchViewModel,
) {
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    val searchText by searchViewModel.searchText.collectAsStateWithLifecycle()
    val isRequestSent by searchViewModel.isRequestSent.collectAsStateWithLifecycle()


    var searchBarWidth by remember { mutableStateOf(if(isRequestSent) 200  else 235 ) }
    var searchBarTrailingIconWidth by remember { mutableStateOf(if(isRequestSent) 72 else 32) }

    val enterTransition = fadeIn(animationSpec = tween(65))
    val exitTransition = fadeOut(animationSpec = tween(65))

    LaunchedEffect(isRequestSent) {
        if(isRequestSent) {
            searchBarTrailingIconWidth = 72
            searchBarWidth = 200
        } else {
            searchBarWidth = 235
            searchBarTrailingIconWidth = 34
        }
    }

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.sdp),
                verticalAlignment = Alignment.CenterVertically
            ){
                SearchField(
                    modifier = Modifier
                        .padding(start = 10.sdp),
                    text = searchText,
                    onTextChange = { value ->
                        searchViewModel.setSearchText(value)
                                   },
                    width = animateIntAsState(targetValue = searchBarWidth).value.sdp,
                    background = colorScheme.inversePrimary,
                    textColor = colorScheme.background,
                    focusedBorder = BorderStroke(0.sdp, Color.Transparent),
                    unfocusedBorder = BorderStroke(0.sdp,Color.Transparent),
                    shape = RoundedCornerShape(8.sdp),
                    onSearchClick = {
                        if(searchText.isNotBlank()) {
                            focusManager.clearFocus()
                            searchViewModel.setRequestStatus(true)
                            searchViewModel.setSearchedData()
                        }
                    },
                    searchIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "search",
                            tint = colorScheme.background,
                            modifier = Modifier
                                .padding(start = 12.sdp)
                                .size(21.sdp)
                                .clip(RoundedCornerShape(8.sdp))
                                .clickable {
                                    if (searchText.isNotBlank()) {
                                        focusManager.clearFocus()
                                        searchViewModel.setRequestStatus(true)
                                        searchViewModel.setSearchedData()
                                    }
                                }
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "clear",
                            tint = colorScheme.background,
                            modifier = Modifier
                                .padding(end = 7.sdp)
                                .size(23.sdp)
                                .clip(RoundedCornerShape(8.sdp))
                                .clickable {
                                    searchViewModel.setSearchText("")
                                }
                        )
                    }
                )

                Box(
                    modifier = Modifier
                        .padding(start = 9.sdp)
                        .width(animateIntAsState(targetValue = searchBarTrailingIconWidth).value.sdp)
                        .height(34.sdp)
                        .clip(RoundedCornerShape(10.sdp))
                        .background(colorScheme.inversePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    if(!isRequestSent){
                        Icon(
                            imageVector = Icons.Filled.History,
                            contentDescription = "history",
                            tint = colorScheme.background,
                            modifier = Modifier
                                .size(30.sdp)
                        )
                    } else {
                        TextButton(
                            modifier = Modifier,
                            text = "Cancel",
                            style = typography().bodyMedium.copy(),
                            width = 72.sdp,
                            height = 34.sdp,
                            background = colorScheme.inversePrimary,
                            borderStroke = BorderStroke(0.sdp, Color.Transparent),
                            onClick = {
                                searchViewModel.setRequestStatus(false)
                                searchViewModel.clearSearchedData()
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AnimatedVisibility(
            visible = !isRequestSent,
            enter = enterTransition,
            exit = exitTransition,
        ) {
            val trendingArtistsResult by searchViewModel.trendingArtists.collectAsStateWithLifecycle()

            DiscoverScreen(
                modifier = Modifier
                    .padding(innerPadding),
                trendingArtistsResult = trendingArtistsResult,
                musicControllerUiState = musicControllerUiState
            )
        }

        AnimatedVisibility(
            visible = isRequestSent,
            enter = enterTransition,
            exit = exitTransition,
        ) {
            val searchedData = searchViewModel.searchedData.collectAsLazyPagingItems()

            SearchedMediaScreen(
                modifier = Modifier
                    .padding(innerPadding),
                paginatedSearchedData = searchedData,
                navigateToSelectedMusic = { id ->
                    scope.launch {
                        val selectedMusicResponse = searchViewModel.getMusicById(id)
                        if(selectedMusicResponse.results.isNotEmpty()) {
                            val selectedMusic = selectedMusicResponse.results.first()

                            if(musicControllerUiState.currentMusic?.audio != selectedMusic.audio) {
                                setPlaylist(DetailedPlaylistObject(selectedMusicResponse.results))
                                onEvent(MusicSelectionEvent.SetMediaItem(selectedMusic))
                                onEvent(MusicSelectionEvent.PlaySong(selectedMusicResponse.results,selectedMusic))
                            }

                            navController.navigate(ScreensRoutes.DetailedScreens.DetailedMusicRoute)
                        }
                    }
                },
                navigateToSelectedPlaylist = { id ->
                    scope.launch {
                        val selectedPlaylistResponse = searchViewModel.getPlaylistById(id)

                        if(selectedPlaylistResponse.results.isNotEmpty()) {
                            val selectedPlaylist = selectedPlaylistResponse.results.first()

                            val playlistTracks = selectedPlaylist.tracks.filter { it.audio != null }

                            if(playlistTracks.isNotEmpty()) {
                                if (selectedPlaylist.id != playlist?.id) {
                                    setPlaylist(selectedPlaylist)
                                    onEvent(MusicSelectionEvent.SetMediaItems(playlistTracks))
                                }

                                navController.navigate(
                                    ScreensRoutes.DetailedScreens.DetailedPlaylistRoute(
                                        parametersJson = Json.encodeToString(selectedPlaylist)
                                    )
                                )
                            }
                        }
                    }
                },
                navigateToSelectedArtist = { id ->
                    scope.launch {
                        val artistResponse = searchViewModel.getArtistById(id)

                        if(artistResponse.results.isNotEmpty()) {
                            val selectedArtist = artistResponse.results.first()
                            val artistTracks = selectedArtist.tracks

                            if(artistTracks.isNotEmpty()) {
                                if (artistTracks != playlist?.tracks) {
                                    setPlaylist(DetailedPlaylistObject(artistTracks))
                                    onEvent(MusicSelectionEvent.SetMediaItems(artistTracks))
                                }

                                navController.navigate(
                                    ScreensRoutes.DetailedScreens.DetailedArtistRoute(
                                        parametersJson = Json.encodeToString(selectedArtist)
                                    )
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}
