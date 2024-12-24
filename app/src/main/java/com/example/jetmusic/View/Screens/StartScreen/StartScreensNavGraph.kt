package com.example.jetmusic.View.Screens.StartScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jetmusic.View.Screens.StartScreen.Screens.LogInScreen
import com.example.jetmusic.View.Screens.StartScreen.Screens.SignUpScreen
import com.example.jetmusic.View.Screens.StartScreen.Screens.WelcomeScreen
import com.example.jetmusic.View.ScreensRoutes

fun NavGraphBuilder.startScreensGraph(
    navController: NavController,
    showBottomBar: (show: Boolean) -> Unit
) {
    showBottomBar(false)

    navigation<ScreensRoutes.StartScreens>(
        startDestination = ScreensRoutes.StartScreens.SignUpRoute
    ) {
        composable<ScreensRoutes.StartScreens.WelcomeRoute> {
            WelcomeScreen(
                navController = navController
            )
        }

        composable<ScreensRoutes.StartScreens.SignUpRoute> {
            SignUpScreen(
                navController = navController
            )
        }

        composable<ScreensRoutes.StartScreens.LogInRoute> {
            LogInScreen(
                navController = navController
            )
        }
    }
}