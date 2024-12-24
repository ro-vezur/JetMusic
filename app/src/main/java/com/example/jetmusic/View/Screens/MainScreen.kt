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
import androidx.navigation.compose.NavHost
import com.example.jetmusic.View.Screens.StartScreen.startScreensGraph
import com.example.jetmusic.View.ScreensRoutes

@Composable
fun MainScreen() {
    val colors = colorScheme

    val navController = rememberNavController()

    var showBottomBar by remember { mutableStateOf(false) }

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
            startDestination = ScreensRoutes.StartScreens
        ) {
            startScreensGraph(
                navController = navController,
                showBottomBar = { showBottomBar = it}
            )
        }
    }
}