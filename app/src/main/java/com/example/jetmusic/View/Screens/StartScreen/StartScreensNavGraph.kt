package com.example.jetmusic.View.Screens.StartScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.View.Screens.StartScreen.Screens.LogInScreen
import com.example.jetmusic.View.Screens.StartScreen.Screens.SignUpScreen
import com.example.jetmusic.View.Screens.StartScreen.Screens.WelcomeScreen
import com.example.jetmusic.View.ScreensRoutes

fun NavGraphBuilder.startScreensGraph(
    navController: NavController,
    showBottomBar: (show: Boolean) -> Unit,
    setUser: (newUser: User) -> Unit
) {

    navigation<ScreensRoutes.StartScreens>(
        startDestination = ScreensRoutes.StartScreens.WelcomeRoute
    ) {

        composable<ScreensRoutes.StartScreens.WelcomeRoute> {
            showBottomBar(false)

            WelcomeScreen(
                navController = navController,
                setUser = setUser,
            )
        }

        composable<ScreensRoutes.StartScreens.SignUpRoute> {
            showBottomBar(false)

            SignUpScreen(
                navController = navController,
                setUser = setUser,
            )
        }

        composable<ScreensRoutes.StartScreens.LogInRoute> {
            showBottomBar(false)

            LogInScreen(
                navController = navController,
                setUser = setUser
            )
        }
    }
}