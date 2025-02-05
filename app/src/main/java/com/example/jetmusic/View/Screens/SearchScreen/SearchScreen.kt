package com.example.jetmusic.View.Screens.SearchScreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetmusic.Extensions.NavigateExtensions.navigateBack
import com.example.jetmusic.Extensions.NavigateExtensions.singleTapNavigate
import com.example.jetmusic.NOT_REQUESTED_SEARCH_BAR_ICON_WIDTH
import com.example.jetmusic.NOT_REQUESTED_SEARCH_BAR_WIDTH
import com.example.jetmusic.REQUESTED_SEARCH_BAR_ICON_WIDTH
import com.example.jetmusic.REQUESTED_SEARCH_BAR_WIDTH
import com.example.jetmusic.View.Components.Buttons.TextButton
import com.example.jetmusic.View.Components.InputFields.SearchField
import com.example.jetmusic.View.Screens.ResultScreens.LoadingScreen
import com.example.jetmusic.View.Screens.SearchScreen.DiscoverScreen.BrowsedMusicList
import com.example.jetmusic.View.Screens.SearchScreen.DiscoverScreen.DiscoverScreen
import com.example.jetmusic.View.ScreensRoutes
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistObject
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistObject
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    currentMusicObject: MusicObject?,
    selectMusic: (MusicObject) -> Unit,
    selectPlaylist: (DetailedPlaylistObject) -> Unit,
    selectArtist: (DetailedArtistObject) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {

    val focusManager = LocalFocusManager.current

    val innerNavController = rememberNavController()
    val scope = rememberCoroutineScope()

    var searchText by remember { mutableStateOf("") }
    var isRequestSent by remember { mutableStateOf(false) }

    var searchBarWidth by remember {
        mutableIntStateOf(NOT_REQUESTED_SEARCH_BAR_WIDTH)
    }
    var searchBarTrailingIconWidth by remember {
        mutableIntStateOf(NOT_REQUESTED_SEARCH_BAR_ICON_WIDTH)
    }
    var showSearchBar by remember { mutableStateOf(true) }

    LaunchedEffect(isRequestSent) {
        if(isRequestSent) {
            searchBarWidth = REQUESTED_SEARCH_BAR_WIDTH
            searchBarTrailingIconWidth = REQUESTED_SEARCH_BAR_ICON_WIDTH
        } else {
            searchBarWidth = NOT_REQUESTED_SEARCH_BAR_WIDTH
            searchBarTrailingIconWidth = NOT_REQUESTED_SEARCH_BAR_ICON_WIDTH
        }
    }

    val searchFunction = {
        if (searchText.isNotBlank()) {
            focusManager.clearFocus()
            isRequestSent = true
            searchViewModel.setSearchedData(searchText,limit = 5)
            innerNavController.navigate(ScreensRoutes.MainSearchRoute.SearchedMediaRoute) {
                popUpTo(ScreensRoutes.MainSearchRoute.SearchedMediaRoute) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            if(showSearchBar) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchField(
                        modifier = Modifier
                            .padding(start = 10.sdp),
                        text = searchText,
                        onTextChange = { value ->
                            searchText = value
                        },
                        width = animateIntAsState(searchBarWidth).value.sdp,
                        background = colorScheme.inversePrimary,
                        textColor = colorScheme.background,
                        focusedBorder = BorderStroke(0.sdp, Color.Transparent),
                        unfocusedBorder = BorderStroke(0.sdp, Color.Transparent),
                        shape = RoundedCornerShape(8.sdp),
                        onSearchClick = searchFunction,
                        searchIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "search",
                                tint = colorScheme.background,
                                modifier = Modifier
                                    .padding(start = 12.sdp)
                                    .size(21.sdp)
                                    .clip(RoundedCornerShape(8.sdp))
                                    .clickable { searchFunction() }
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
                                        searchText = ""
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
                        if (!isRequestSent) {
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
                                width = REQUESTED_SEARCH_BAR_ICON_WIDTH.sdp,
                                height = 34.sdp,
                                background = colorScheme.inversePrimary,
                                borderStroke = BorderStroke(0.sdp, Color.Transparent),
                                onClick = {
                                    isRequestSent = false
                                    searchViewModel.clearSearchedData()
                                    innerNavController.navigate(ScreensRoutes.MainSearchRoute.DiscoverRoute) {
                                        launchSingleTop = true
                                        restoreState = true
                                        popUpTo(ScreensRoutes.MainSearchRoute.SearchedMediaRoute) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = innerNavController,
            startDestination = ScreensRoutes.MainSearchRoute.DiscoverRoute,
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 250)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 250)) },
        ) {
            composable<ScreensRoutes.MainSearchRoute.DiscoverRoute> {
                val trendingArtistsResult by searchViewModel.trendingArtists.collectAsStateWithLifecycle()

                showSearchBar = true

                DiscoverScreen(
                    modifier = Modifier
                        .padding(innerPadding),
                    trendingArtistsResult = trendingArtistsResult,
                    currentMusicObject = currentMusicObject,
                    selectArtist = { id ->
                        scope.launch {
                            val detailedArtistResponse = searchViewModel.getArtistById(id)
                            if(detailedArtistResponse.results.isNotEmpty()) {
                                val detailedArtistObject = detailedArtistResponse.results.first()
                                selectArtist(detailedArtistObject)
                            }
                        }
                    },
                    setDiscoveredSongsByGenre = { genre ->
                        searchViewModel.discoverSongs(genre.tag)
                        innerNavController.singleTapNavigate(
                            ScreensRoutes.MainSearchRoute.BrowsedMusicListRoute(genre.displayName)
                        )
                    }
                )
            }

            composable<ScreensRoutes.MainSearchRoute.SearchedMediaRoute> {
                val searchedData = searchViewModel.searchedData.collectAsLazyPagingItems()

                showSearchBar = true

                when {
                    searchedData.loadState.refresh is LoadState.Loading -> { LoadingScreen(Modifier.fillMaxSize()) }
                }

                BackHandler {
                    innerNavController.navigateBack()
                    searchText = ""
                    isRequestSent = false
                }

                SearchedMediaScreen(
                    modifier = Modifier
                        .padding(innerPadding),
                    currentMusic = currentMusicObject,
                    paginatedSearchedData = searchedData,
                    navigateToSelectedMusic = { id ->
                        scope.launch {
                            val selectedMusicResponse = searchViewModel.getMusicById(id)
                            if(selectedMusicResponse.results.isNotEmpty()) {
                                val musicToSelect = selectedMusicResponse.results.first()

                                selectMusic(musicToSelect)
                            }
                        }
                    },
                    navigateToSelectedPlaylist = { id ->
                        scope.launch {
                            val selectedPlaylistResponse = searchViewModel.getPlaylistById(id)

                            if(selectedPlaylistResponse.results.isNotEmpty()) {
                                val playlistObject = selectedPlaylistResponse.results.first()

                                val playlistTracks = playlistObject.tracks.filter { it.audio != null }

                                if(playlistTracks.isNotEmpty()) {
                                    selectPlaylist(playlistObject)
                                }
                            }
                        }
                    },
                    navigateToSelectedArtist = { id ->
                        scope.launch {
                            val artistResponse = searchViewModel.getArtistById(id)

                            if(artistResponse.results.isNotEmpty()) {
                                val artistToSelect = artistResponse.results.first()
                                val artistTracks = artistToSelect.tracks

                                if(artistTracks.isNotEmpty()) {
                                    selectArtist(artistToSelect)
                                }
                            }
                        }
                    }
                )
            }

            composable<ScreensRoutes.MainSearchRoute.BrowsedMusicListRoute> { backStackEntry ->
                val arguments = backStackEntry.toRoute<ScreensRoutes.MainSearchRoute.BrowsedMusicListRoute>()
                val paginatedDiscoveredSongs = searchViewModel.discoveredSongsByGenre.collectAsLazyPagingItems()

                showSearchBar = false

                BrowsedMusicList(
                    modifier = Modifier
                        .padding(horizontal = 12.sdp),
                    currentMusic = currentMusicObject,
                    genre = arguments.genre,
                    paginatedSongs = paginatedDiscoveredSongs,
                    turnBack = {
                        innerNavController.navigateBack()
                    },
                    navigateToSelectedMusic = selectMusic
                )
            }
        }
    }
}
