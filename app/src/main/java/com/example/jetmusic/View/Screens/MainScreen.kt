package com.example.jetmusic.View.Screens

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.jetmusic.View.Components.BottomNaviationBar.BottomNavigationBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.View.Screens.DetailedScreens.MusicDetailedScreen
import com.example.jetmusic.View.Screens.HomeScreen.HomeScreen
import com.example.jetmusic.View.Screens.SearchScreen.SearchScreen
import com.example.jetmusic.View.Screens.StartScreen.startScreensGraph
import com.example.jetmusic.View.ScreensRoutes
import com.example.jetmusic.ViewModels.DetailedScreensViewModels.MusicDetailedViewModel
import com.example.jetmusic.ViewModels.MainScreensViewModels.SearchViewModel
import com.example.jetmusic.ViewModels.UserViewModel

@Composable
fun MainScreen() {
    val colors = colorScheme

    val navController = rememberNavController()

    var showBottomBar by remember { mutableStateOf(false) }

    val userViewModel: UserViewModel = hiltViewModel()
    val firebaseUser by userViewModel.firebaseUser.collectAsStateWithLifecycle()

    val initialRoute = if(firebaseUser != null) ScreensRoutes.HomeRoute else ScreensRoutes.StartScreens

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    Scaffold(
        containerColor = colors.background,
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar
            ){
                BottomNavigationBar(
                    navController = navController
                )
            }
        }
    ) { innerPadding ->
        NavHost(
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

                user?.let { checkedUser ->
                    HomeScreen(
                        navController = navController,
                        user = checkedUser
                    )
                }
            }

            composable<ScreensRoutes.SearchRoute> {
                showBottomBar = true

                val searchViewModel: SearchViewModel = hiltViewModel(viewModelStoreOwner)

                SearchScreen(
                    navController = navController,
                    searchViewModel = searchViewModel
                )
            }

            composable<ScreensRoutes.LibraryRoute> {
                showBottomBar = true
            }

            composable<MusicObject> { navBackStackEntry ->
                showBottomBar = false

                val musicObject: MusicObject = navBackStackEntry.toRoute()

                MusicDetailedScreen(
                    navController = navController,
                    musicObject = musicObject,
                    musicDetailedViewModel = hiltViewModel(viewModelStoreOwner)
                )
            }
        }
    }
}