package com.example.jetmusic.View.Screens

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.jetmusic.BOTTOM_MUSIC_PLAYER_HEIGHT
import com.example.jetmusic.Extensions.NavigateExtensions.navigateBack
import com.example.jetmusic.Extensions.NavigateExtensions.singleTapNavigate
import com.example.jetmusic.Helpers.MusicHelper
import com.example.jetmusic.SharedViewModels.SharedMusicControllerViewModel
import com.example.jetmusic.SharedViewModels.SharedMusicSelectionViewModel
import com.example.jetmusic.SharedViewModels.UserViewModel
import com.example.jetmusic.View.Components.BottomBar.MusicBar.MusicBottomBar
import com.example.jetmusic.View.Components.BottomBar.Navigation.BottomNavigationBar
import com.example.jetmusic.View.Screens.DetailedScreens.DetailedArtistScreen.DetailedArtistScreen
import com.example.jetmusic.View.Screens.DetailedScreens.DetailedMusicScreen.MusicDetailedScreen
import com.example.jetmusic.View.Screens.DetailedScreens.DetailedPlaylistScreen.DetailedPlaylistScreen
import com.example.jetmusic.View.Screens.HomeScreen.HomeScreen
import com.example.jetmusic.View.Screens.MusicLibraryScreen.musicLibraryNavigationGraph
import com.example.jetmusic.View.Screens.SearchScreen.SearchScreen
import com.example.jetmusic.View.Screens.StartScreen.startScreensGraph
import com.example.jetmusic.View.ScreensRoutes
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistObject
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistObject
import com.example.jetmusic.data.DTOs.API.UnifiedData.MediaTypes
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.other.events.MusicSelectionEvent
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import ir.kaaveh.sdpcompose.sdp
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun MainScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    sharedMusicControllerViewModel: SharedMusicControllerViewModel,
    sharedMusicSelectionViewModel: SharedMusicSelectionViewModel = hiltViewModel(),
) {

    Log.d("recomposition","Recompose!")

    val hazeState = remember { HazeState() }
    val navController = rememberNavController()
    var showBottomBar by remember { mutableStateOf(false) }


    val firebaseUser by userViewModel.firebaseUser.collectAsStateWithLifecycle()

    val musicControllerUiState by sharedMusicControllerViewModel.musicControllerUiState.collectAsStateWithLifecycle()

    val initialRoute by remember(firebaseUser) {
        mutableStateOf(if(firebaseUser != null) ScreensRoutes.HomeRoute else ScreensRoutes.StartScreens)
    }

    val selectedMediaType by sharedMusicSelectionViewModel.selectedMediaType.collectAsStateWithLifecycle()
    val selectedPlaylist by sharedMusicSelectionViewModel.selectedPlaylist.collectAsStateWithLifecycle()
    val selectedArtist by sharedMusicSelectionViewModel.selectedArtist.collectAsStateWithLifecycle()

    val selectMusic = { musicToSelect: MusicObject ->
        if(MusicHelper.getTrackIdFromUrl(musicControllerUiState.currentMusic?.audio) != musicToSelect.id) {
            sharedMusicSelectionViewModel.onEvent(MusicSelectionEvent.SetMediaItem(musicToSelect))
            sharedMusicSelectionViewModel.onEvent(MusicSelectionEvent.PauseMusic)
        }

        sharedMusicSelectionViewModel.setMediaType(MediaTypes.MUSIC)
        sharedMusicSelectionViewModel.setMusic(musicToSelect)
        sharedMusicSelectionViewModel.setPlaylist(null)
        sharedMusicSelectionViewModel.setArtist(null)

        navController.singleTapNavigate(ScreensRoutes.DetailedScreens.DetailedMusicRoute)
    }

    val selectPlaylist = { playlistToSelect: DetailedPlaylistObject ->
        if (playlistToSelect.id != selectedPlaylist?.id) {
            sharedMusicSelectionViewModel.onEvent(MusicSelectionEvent.SetMediaItems(playlistToSelect.tracks))
            sharedMusicSelectionViewModel.onEvent(MusicSelectionEvent.PauseMusic)
        }

        navController.singleTapNavigate(
            ScreensRoutes.DetailedScreens.DetailedPlaylistRoute(
                parametersJson = Json.encodeToString(playlistToSelect)
            )
        )
    }

    val selectArtist = { artistToSelect: DetailedArtistObject ->
        if (artistToSelect.id != selectedArtist?.id) {
            sharedMusicSelectionViewModel.onEvent(MusicSelectionEvent.SetMediaItems(artistToSelect.tracks))
            sharedMusicSelectionViewModel.onEvent(MusicSelectionEvent.PauseMusic)
        }

        navController.singleTapNavigate(
            ScreensRoutes.DetailedScreens.DetailedArtistRoute(
                parametersJson = Json.encodeToString(artistToSelect)
            )
        )
    }

    Scaffold(
        containerColor = colorScheme.background,
        bottomBar = {
            if(showBottomBar) {
                Column {
                    MusicBottomBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(BOTTOM_MUSIC_PLAYER_HEIGHT.sdp)
                            .hazeChild(hazeState)
                            .clickable {
                                when (selectedMediaType) {
                                    MediaTypes.MUSIC -> {
                                        navController.singleTapNavigate(ScreensRoutes.DetailedScreens.DetailedMusicRoute)
                                    }

                                    MediaTypes.ARTIST -> {
                                        navController.singleTapNavigate(
                                            ScreensRoutes.DetailedScreens.DetailedArtistRoute(
                                                Json.encodeToString(selectedArtist)
                                            )
                                        )
                                    }

                                    MediaTypes.PLAYLIST -> {
                                        navController.singleTapNavigate(
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
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 300)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) },
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

                HomeScreen(
                    navController = navController,
                    user = user?: User(),
                    selectMusic = selectMusic,
                )
            }

            composable<ScreensRoutes.MainSearchRoute> {
                showBottomBar = true

                SearchScreen(
                    currentMusicObject = musicControllerUiState.currentMusic,
                    selectMusic = selectMusic,
                    selectPlaylist = selectPlaylist,
                    selectArtist = selectArtist,
                )
            }

            musicLibraryNavigationGraph(
                navController = navController,
                currentMusicObject = musicControllerUiState.currentMusic,
                showBottomBar = { show -> showBottomBar = show},
                selectMusic = selectMusic,
                selectPlaylist = selectPlaylist,
                selectArtist = selectArtist,
                logOut = {
                    userViewModel.logOut()
                    sharedMusicSelectionViewModel.onEvent(MusicSelectionEvent.ClearMediaItems)
                }
            )

            composable<ScreensRoutes.DetailedScreens.DetailedMusicRoute> { navBackStackEntry ->
                showBottomBar = false

                val artistTracks = selectedArtist?.tracks
                val playlistTracks = selectedPlaylist?.tracks

                musicControllerUiState.currentMusic?.let { currentMusic ->

                    val user by userViewModel.user.collectAsStateWithLifecycle()

                    user?.let { checkedUser ->
                        LaunchedEffect(null) {
                            val findInArtistTracks = artistTracks?.find { MusicHelper.getTrackIdFromUrl(currentMusic.audio) == it.id }
                            val findInPlaylistTracks = playlistTracks?.find { MusicHelper.getTrackIdFromUrl(currentMusic.audio) == it.id  }

                            if(findInArtistTracks == null && findInPlaylistTracks == null) {
                                sharedMusicSelectionViewModel.setMusic(currentMusic)
                                sharedMusicSelectionViewModel.setMediaType(MediaTypes.MUSIC)
                                sharedMusicSelectionViewModel.setArtist(null)
                                sharedMusicSelectionViewModel.setPlaylist(null)
                            }
                        }

                        MusicDetailedScreen(
                            musicControllerUiState = musicControllerUiState,
                            user = checkedUser,
                            navigateBack = { navController.navigateBack() },
                            updateUser = userViewModel::updateUser,
                        )
                    }
                }
            }

            composable<ScreensRoutes.DetailedScreens.DetailedPlaylistRoute> { navBackStackEntry ->
                showBottomBar = false

                val user by userViewModel.user.collectAsStateWithLifecycle()

                val playlistObjectJson = navBackStackEntry.toRoute<ScreensRoutes.DetailedScreens.DetailedPlaylistRoute>()
                val playlistObject: DetailedPlaylistObject = Json.decodeFromString(playlistObjectJson.parametersJson)

                if(selectedPlaylist?.id != playlistObject.id) {
                    sharedMusicSelectionViewModel.setPlaylist(playlistObject)
                    sharedMusicSelectionViewModel.setMediaType(MediaTypes.PLAYLIST)
                    sharedMusicSelectionViewModel.setArtist(null)
                }

                user?.let { checkedUser ->
                    DetailedPlaylistScreen(
                        navController = navController,
                        playlistObject = playlistObject,
                        musicControllerUiState = musicControllerUiState,
                        user = checkedUser,
                        updateUser = userViewModel::updateUser
                    )
                }
            }

            composable<ScreensRoutes.DetailedScreens.DetailedArtistRoute> { navBackStackEntry ->
                showBottomBar = false

                val user by userViewModel.user.collectAsStateWithLifecycle()
                val selectedArtistObject by sharedMusicSelectionViewModel.selectedArtist.collectAsStateWithLifecycle()

                val artistObjectJson = navBackStackEntry.toRoute<ScreensRoutes.DetailedScreens.DetailedArtistRoute>()
                val artistObject: DetailedArtistObject = Json.decodeFromString(artistObjectJson.parametersJson)

                if(selectedArtistObject?.id != artistObject.id) {
                    sharedMusicSelectionViewModel.setArtist(artistObject)
                    sharedMusicSelectionViewModel.setMediaType(MediaTypes.ARTIST)
                    sharedMusicSelectionViewModel.setPlaylist(null)
                }

                user?.let { checkedUser ->
                    DetailedArtistScreen(
                        navController = navController,
                        artistObject = artistObject,
                        musicControllerUiState = musicControllerUiState,
                        user = checkedUser,
                        updateUser = userViewModel::updateUser
                    )
                }
            }
        }
    }
}