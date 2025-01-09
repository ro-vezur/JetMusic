package com.example.jetmusic.View.Screens.MusicLibraryScreen

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jetmusic.View.ScreenRoutes.ScreensRoutes
import com.example.jetmusic.View.Screens.MusicLibraryScreen.Screens.LikedSongsScreen
import com.example.jetmusic.View.Screens.MusicLibraryScreen.Screens.MainLibraryScreen
import com.example.jetmusic.ViewModels.LikedSongsViewModel
import com.example.jetmusic.ViewModels.SharedViewModels.UserViewModel
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject

fun NavGraphBuilder.musicLibraryNavigationGraph(
    navController: NavController,
    currentMusicObject: MusicObject?,
    viewModelStoreOwner: ViewModelStoreOwner,
    showBottomBar: (Boolean) -> Unit,
    selectMusic: (MusicObject) -> Unit,
) {
    navigation<ScreensRoutes.LibraryNavigationGraph>(
        startDestination = ScreensRoutes.LibraryNavigationGraph.LibraryRoute
    ) {
        composable<ScreensRoutes.LibraryNavigationGraph.LibraryRoute> {
            showBottomBar(true)

            val userViewModel: UserViewModel = hiltViewModel(viewModelStoreOwner)
            val user by userViewModel.user.collectAsStateWithLifecycle()

            user?.let { checkedUser ->
                MainLibraryScreen(
                    navController = navController,
                    user = checkedUser
                )
            }
        }

        composable<ScreensRoutes.LibraryNavigationGraph.LikedSongs> {
            showBottomBar(true)

            val userViewModel: UserViewModel = hiltViewModel(viewModelStoreOwner)
            val user by userViewModel.user.collectAsStateWithLifecycle()

            user?.let { checkedUser ->
                val likedSongsViewModel: LikedSongsViewModel = hiltViewModel(viewModelStoreOwner)
                val likedSongsList by  likedSongsViewModel.likedSongsList.collectAsStateWithLifecycle()

                LaunchedEffect(null) {
                    if(likedSongsList.map { it.id } != checkedUser.likedSongsIds) {
                        likedSongsViewModel.setSongsList(checkedUser.likedSongsIds)
                    }
                }

                LikedSongsScreen(
                    navController = navController,
                    currentMusicObject = currentMusicObject,
                    likedMusicList = likedSongsList,
                    selectMusic = selectMusic,
                )
            }
        }

        composable<ScreensRoutes.LibraryNavigationGraph.LikedPlaylists> {
            showBottomBar(true)


        }

        composable<ScreensRoutes.LibraryNavigationGraph.LikedArtists> {
            showBottomBar(true)


        }

        composable<ScreensRoutes.LibraryNavigationGraph.Downloads> {
            showBottomBar(true)


        }
    }
}