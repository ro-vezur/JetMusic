package com.example.jetmusic.View.Screens.MusicLibraryScreen

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
import com.example.jetmusic.View.Screens.MusicLibraryScreen.Screens.LikedArtistsScreen
import com.example.jetmusic.View.Screens.MusicLibraryScreen.Screens.LikedPlaylistsScreen
import com.example.jetmusic.View.Screens.MusicLibraryScreen.Screens.LikedSongsScreen
import com.example.jetmusic.View.Screens.MusicLibraryScreen.Screens.MainLibraryScreen
import com.example.jetmusic.ViewModels.LibraryScreenViewModels.LikedArtistsViewModel
import com.example.jetmusic.ViewModels.LibraryScreenViewModels.LikedPlaylistsViewModel
import com.example.jetmusic.ViewModels.LibraryScreenViewModels.LikedSongsViewModel
import com.example.jetmusic.ViewModels.SharedViewModels.UserViewModel
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistObject
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistObject

fun NavGraphBuilder.musicLibraryNavigationGraph(
    navController: NavController,
    currentMusicObject: MusicObject?,
    viewModelStoreOwner: ViewModelStoreOwner,
    showBottomBar: (Boolean) -> Unit,
    selectMusic: (MusicObject) -> Unit,
    selectPlaylist: (DetailedPlaylistObject) -> Unit,
    selectArtist: (DetailedArtistObject) -> Unit,
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
                    if(likedSongsList.data?.map { it.id } != checkedUser.likedSongsIds) {
                        likedSongsViewModel.setSongsList(checkedUser.likedSongsIds)
                    }
                }

                LikedSongsScreen(
                    navController = navController,
                    currentMusicObject = currentMusicObject,
                    likedSongsCount = checkedUser.likedSongsIds.size,
                    likedMusicResult = likedSongsList,
                    selectMusic = selectMusic,
                )
            }
        }

        composable<ScreensRoutes.LibraryNavigationGraph.LikedPlaylists> {
            showBottomBar(true)

            val userViewModel: UserViewModel = hiltViewModel(viewModelStoreOwner)
            val user by userViewModel.user.collectAsStateWithLifecycle()

            user?.let { checkedUser ->
                val likedPlaylistsViewModel: LikedPlaylistsViewModel = hiltViewModel(viewModelStoreOwner)
                val likedPlaylistsList by likedPlaylistsViewModel.likedPlaylistsResult.collectAsStateWithLifecycle()

                LaunchedEffect(null) {
                    if (likedPlaylistsList.data?.map { it.id } != checkedUser.likedPlaylistsIds) {
                        likedPlaylistsViewModel.setPlaylists(checkedUser.likedPlaylistsIds)
                    }
                }

                LikedPlaylistsScreen(
                    navController = navController,
                    currentMusicObject = currentMusicObject,
                    likedPlaylistsCount = checkedUser.likedPlaylistsIds.size,
                    likedPlaylistsResult = likedPlaylistsList,
                    selectPlaylist = selectPlaylist,
                )
            }
        }

        composable<ScreensRoutes.LibraryNavigationGraph.LikedArtists> {
            showBottomBar(true)

            val userViewModel: UserViewModel = hiltViewModel(viewModelStoreOwner)
            val user by userViewModel.user.collectAsStateWithLifecycle()

            user?.let { checkedUser ->
                val likedArtistsViewModel: LikedArtistsViewModel = hiltViewModel(viewModelStoreOwner)
                val likedArtistsResult by likedArtistsViewModel.likedArtistsResult.collectAsStateWithLifecycle()

                LaunchedEffect(null) {
                    if(likedArtistsResult.data?.map { it.id } != checkedUser.likedArtistsIds) {
                        likedArtistsViewModel.setLikedArtists(checkedUser.likedArtistsIds)
                    }
                }

                LikedArtistsScreen(
                    navController = navController,
                    currentMusicObject = currentMusicObject,
                    likedArtistsCount = checkedUser.likedArtistsIds.size,
                    likedArtistsResult = likedArtistsResult,
                    selectArtist = selectArtist
                )
            }
        }

        composable<ScreensRoutes.LibraryNavigationGraph.Downloads> {
            showBottomBar(true)


        }
    }
}