package com.example.jetmusic.View.Screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.jetmusic.View.Components.BottomBar.Navigation.BottomNavigationBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.jetmusic.BOTTOM_MUSIC_PLAYER_HEIGHT
import com.example.jetmusic.Extensions.NavigateExtensions.navigateBack
import com.example.jetmusic.Helpers.SerializationClass
import com.example.jetmusic.View.Components.BottomBar.MusicBar.MusicBottomBar
import com.example.jetmusic.View.Screens.DetailedScreens.DetailedMusicScreen.MusicDetailedScreen
import com.example.jetmusic.View.Screens.DetailedScreens.DetailedPlaylistScreen.DetailedPlaylistScreen
import com.example.jetmusic.View.Screens.HomeScreen.HomeScreen
import com.example.jetmusic.View.Screens.SearchScreen.SearchScreen
import com.example.jetmusic.View.Screens.StartScreen.startScreensGraph
import com.example.jetmusic.View.ScreenRoutes.ScreensRoutes
import com.example.jetmusic.View.Screens.DetailedScreens.DetailedArtistScreen.DetailedArtistScreen
import com.example.jetmusic.ViewModels.MusicPlayerViewModel
import com.example.jetmusic.ViewModels.MainScreensViewModels.SearchViewModel
import com.example.jetmusic.ViewModels.SharedViewModels.SharedMusicControllerViewModel
import com.example.jetmusic.ViewModels.SharedViewModels.SharedMusicSelectionViewModel
import com.example.jetmusic.ViewModels.SharedViewModels.UserViewModel
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistObject
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistObject
import com.example.jetmusic.data.DTOs.API.UnifiedData.MediaTypes
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import ir.kaaveh.sdpcompose.sdp
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun MainScreen(
    sharedMusicControllerViewModel: SharedMusicControllerViewModel,
    sharedMusicSelectionViewModel: SharedMusicSelectionViewModel = hiltViewModel(),
) {
    val colors = colorScheme

    val hazeState = remember { HazeState() }
    val navController = rememberNavController()
    var showBottomBar by remember { mutableStateOf(false) }

    val userViewModel: UserViewModel = hiltViewModel()
    val firebaseUser by userViewModel.firebaseUser.collectAsStateWithLifecycle()

    val musicControllerUiState = sharedMusicControllerViewModel.musicControllerUiState

    val initialRoute = if(firebaseUser != null) ScreensRoutes.HomeRoute else ScreensRoutes.StartScreens

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    Scaffold(
        containerColor = colors.background,
        bottomBar = {
            if(showBottomBar) {
                val selectedMediaType by sharedMusicSelectionViewModel.selectedMediaType.collectAsStateWithLifecycle()
                val selectedPlaylist by sharedMusicSelectionViewModel.selectedPlaylist.collectAsStateWithLifecycle()
                val selectedArtist by sharedMusicSelectionViewModel.selectedArtist.collectAsStateWithLifecycle()

                Column {
                    MusicBottomBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(BOTTOM_MUSIC_PLAYER_HEIGHT.sdp)
                            .hazeChild(hazeState)
                            .clickable {
                                when (selectedMediaType) {
                                    MediaTypes.MUSIC -> {
                                        navController.navigate(ScreensRoutes.DetailedScreens.DetailedMusicRoute)
                                    }

                                    MediaTypes.ARTIST -> {
                                        navController.navigate(
                                            ScreensRoutes.DetailedScreens.DetailedArtistRoute(
                                                parametersJson = Json.encodeToString(
                                                    selectedArtist
                                                )
                                            )
                                        )
                                    }

                                    MediaTypes.PLAYLIST -> {
                                        navController.navigate(
                                            ScreensRoutes.DetailedScreens.DetailedPlaylistRoute(
                                                Json.encodeToString(selectedPlaylist)
                                            )
                                        )
                                    }
                                }
                            },
                        musicControllerUiState = musicControllerUiState,
                    )

                    BottomNavigationBar(
                        navController = navController
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .haze(
                    hazeState,
                    backgroundColor = colorScheme.background,
                    tint = Color.Black.copy(alpha = .2f),
                    blurRadius = 6.sdp,
                ) ,
            navController = navController,
            startDestination = initialRoute,
        ) {
            startScreensGraph(
                navController = navController,
                showBottomBar = { showBottomBar = it},
                setUser = { newUser ->
                    userViewModel.setUser(newUser)
                }
            )

            composable<ScreensRoutes.HomeRoute> {
                showBottomBar = true

                val user by userViewModel.user.collectAsStateWithLifecycle()
                val selectedPlaylist by sharedMusicSelectionViewModel.selectedPlaylist.collectAsStateWithLifecycle()

                user?.let { checkedUser ->
                    HomeScreen(
                        navController = navController,
                        user = checkedUser,
                        musicControllerUiState = musicControllerUiState,
                        playlist = selectedPlaylist,
                        setPlaylist = sharedMusicSelectionViewModel::setPlaylist,
                        onEvent = sharedMusicSelectionViewModel::onEvent
                    )
                }
            }

            composable<ScreensRoutes.SearchRoute> {
                showBottomBar = true

                val searchViewModel: SearchViewModel = hiltViewModel(viewModelStoreOwner)

                val playerMusicList by sharedMusicSelectionViewModel.selectedPlaylist.collectAsStateWithLifecycle()
                val selectedArtist by sharedMusicSelectionViewModel.selectedArtist.collectAsStateWithLifecycle()

                SearchScreen(
                    navController = navController,
                    musicControllerUiState = musicControllerUiState,
                    selectedPlaylist = playerMusicList,
                    selectedArtist = selectedArtist,
                    onEvent = sharedMusicSelectionViewModel::onEvent,
                    searchViewModel = searchViewModel,
                )
            }

            composable<ScreensRoutes.LibraryRoute> {
                showBottomBar = true
            }

            composable<ScreensRoutes.DetailedScreens.DetailedMusicRoute> { navBackStackEntry ->
                showBottomBar = false

                val musicDetailedViewModel: MusicPlayerViewModel = hiltViewModel()
                val selectedArtist by sharedMusicSelectionViewModel.selectedArtist.collectAsStateWithLifecycle()
                val selectedPlaylist by sharedMusicSelectionViewModel.selectedPlaylist.collectAsStateWithLifecycle()

                val artistTracks = selectedArtist?.tracks
                val playlistTracks = selectedPlaylist?.tracks

                musicControllerUiState.currentMusic?.let { currentMusic ->

                    LaunchedEffect(null) {
                        val findInArtistTracks = artistTracks?.find { currentMusic.audio?.contains(it.id) == true }
                        val findInPlaylistTracks = playlistTracks?.find { currentMusic.audio?.contains(it.id) == true }

                        if(findInArtistTracks == null && findInPlaylistTracks == null) {
                            sharedMusicSelectionViewModel.setMusic(currentMusic)
                        }
                    }

                    MusicDetailedScreen(
                        musicControllerUiState = musicControllerUiState,
                        navigateBack = { navController.navigateBack() },
                        onEvent = musicDetailedViewModel::onEvent,
                    )
                }
            }

            composable<ScreensRoutes.DetailedScreens.DetailedPlaylistRoute> { navBackStackEntry ->
                showBottomBar = false

                val selectedPlaylist by sharedMusicSelectionViewModel.selectedPlaylist.collectAsStateWithLifecycle()

                val playlistObjectJson = navBackStackEntry.toRoute<ScreensRoutes.DetailedScreens.DetailedPlaylistRoute>()
                val playlistObject: DetailedPlaylistObject = SerializationClass.decode(playlistObjectJson.parametersJson)

                if(selectedPlaylist?.id != playlistObject.id) {
                    sharedMusicSelectionViewModel.setPlaylist(playlistObject)
                }

                DetailedPlaylistScreen(
                    navController = navController,
                    playlistObject = playlistObject,
                    musicControllerUiState = musicControllerUiState,
                )
            }

            composable<ScreensRoutes.DetailedScreens.DetailedArtistRoute> { navBackStackEntry ->
                showBottomBar = false

                val selectedArtistObject by sharedMusicSelectionViewModel.selectedArtist.collectAsStateWithLifecycle()

                val artistObjectJson = navBackStackEntry.toRoute<ScreensRoutes.DetailedScreens.DetailedArtistRoute>()
                val artistObject: DetailedArtistObject = SerializationClass.decode(artistObjectJson.parametersJson)

                if(selectedArtistObject?.id != artistObject.id) {
                    sharedMusicSelectionViewModel.setArtist(artistObject)
                }

                DetailedArtistScreen(
                    navController = navController,
                    artistObject = artistObject,
                    musicControllerUiState = musicControllerUiState
                )
            }
        }
    }
}