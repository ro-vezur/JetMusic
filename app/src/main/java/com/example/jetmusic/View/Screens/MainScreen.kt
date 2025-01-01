package com.example.jetmusic.View.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.jetmusic.View.Components.BottomBar.Navigation.BottomNavigationBar
import androidx.compose.material3.MaterialTheme.colorScheme
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
import com.example.jetmusic.BOTTOM_MUSIC_PLAYER_HEIGHT
import com.example.jetmusic.Extensions.NavigateExtensions.navigateBack
import com.example.jetmusic.View.Components.BottomBar.MusicBar.MusicBottomBar
import com.example.jetmusic.View.Screens.DetailedScreens.DetailedMusicScreen.MusicDetailedScreen
import com.example.jetmusic.View.Screens.HomeScreen.HomeScreen
import com.example.jetmusic.View.Screens.SearchScreen.SearchScreen
import com.example.jetmusic.View.Screens.StartScreen.startScreensGraph
import com.example.jetmusic.View.ScreensRoutes
import com.example.jetmusic.ViewModels.DetailedScreensViewModels.MusicDetailedViewModel
import com.example.jetmusic.ViewModels.MainScreensViewModels.SearchViewModel
import com.example.jetmusic.ViewModels.SharedViewModels.SharedMusicControllerViewModel
import com.example.jetmusic.ViewModels.SharedViewModels.UserViewModel
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import ir.kaaveh.sdpcompose.sdp

@Composable
fun MainScreen(
    sharedMusicControllerViewModel: SharedMusicControllerViewModel,
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
                Column {
                    MusicBottomBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(BOTTOM_MUSIC_PLAYER_HEIGHT.sdp)
                            .hazeChild(hazeState)
                            .clickable {
                                navController.navigate(ScreensRoutes.DetailedMusicRoute)
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
                val selectedPlaylist by sharedMusicControllerViewModel.selectedPlaylist.collectAsStateWithLifecycle()

                user?.let { checkedUser ->
                    HomeScreen(
                        modifier = Modifier,
                        navController = navController,
                        user = checkedUser,
                        musicControllerUiState = musicControllerUiState,
                        selectedPlaylist = selectedPlaylist,
                        setPlaylist = { newPlaylist -> sharedMusicControllerViewModel.setPlaylist(newPlaylist)}
                    )
                }
            }

            composable<ScreensRoutes.SearchRoute> {
                showBottomBar = true

                val searchViewModel: SearchViewModel = hiltViewModel(viewModelStoreOwner)
                val selectedPlaylist by sharedMusicControllerViewModel.selectedPlaylist.collectAsStateWithLifecycle()

                SearchScreen(
                    navController = navController,
                    musicControllerUiState = musicControllerUiState,
                    selectedPlaylist = selectedPlaylist,
                    setPlaylist = { newPlaylist -> sharedMusicControllerViewModel.setPlaylist(newPlaylist)},
                    searchViewModel = searchViewModel,
                )
            }

            composable<ScreensRoutes.LibraryRoute> {
                showBottomBar = true
            }

            composable<ScreensRoutes.DetailedMusicRoute> { navBackStackEntry ->
                showBottomBar = false

                val musicDetailedViewModel: MusicDetailedViewModel = hiltViewModel()

                MusicDetailedScreen(
                    musicControllerUiState = musicControllerUiState,
                    navigateBack = { navController.navigateBack() },
                    onEvent = musicDetailedViewModel::onEvent,
                )
            }
        }
    }
}