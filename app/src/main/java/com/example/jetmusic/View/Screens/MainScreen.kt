package com.example.jetmusic.View.Screens

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetmusic.View.Components.Buttons.TextButton
import com.example.jetmusic.View.Screens.StartScreen.HomeScreen.HomeScreen
import com.example.jetmusic.View.Screens.StartScreen.startScreensGraph
import com.example.jetmusic.View.ScreensRoutes
import com.example.jetmusic.ViewModels.StartScreenViewModels.UserViewModel

@Composable
fun MainScreen() {
    val colors = colorScheme

    val navController = rememberNavController()

    var showBottomBar by remember { mutableStateOf(false) }

    val userViewModel: UserViewModel = hiltViewModel()
    val firebaseUser by userViewModel.firebaseUser.collectAsStateWithLifecycle()

    val initialRoute = if(firebaseUser != null) ScreensRoutes.HomeRoute else ScreensRoutes.StartScreens

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

                TextButton(
                    text = "Log Out",
                    onClick = {
                        userViewModel.logOut()
                    }
                )

                HomeScreen(

                )
            }
        }
    }
}