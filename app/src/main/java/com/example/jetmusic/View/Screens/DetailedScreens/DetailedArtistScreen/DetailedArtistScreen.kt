package com.example.jetmusic.View.Screens.DetailedScreens.DetailedArtistScreen

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jetmusic.Extensions.NavigateExtensions.navigateBack
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistObject
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetmusic.View.ScreenRoutes.ScreensRoutes
import com.example.jetmusic.data.DTOs.UserDTOs.User

@Composable
fun DetailedArtistScreen(
    navController: NavController,
    artistObject: DetailedArtistObject,
    musicControllerUiState: MusicControllerUiState,
    user: User,
    updateUser: (User) -> Unit,
) {
    val innerNavController = rememberNavController()
    val currentBackStackEntry by innerNavController.currentBackStackEntryAsState()

    var showAllTracks by remember { mutableStateOf(false) }
    var mainInfoScreenHeight by rememberSaveable { mutableStateOf(0.68f) }
    var iconRotateDegrees by rememberSaveable { mutableStateOf(0f) }

    LaunchedEffect(currentBackStackEntry) {
        val currentRoute = currentBackStackEntry?.destination?.route.toString()

        if(currentRoute.contains("ArtistMoreTracksRoute")) {
            mainInfoScreenHeight = 0.84f
            iconRotateDegrees = -90f
            showAllTracks = true
        } else {
            mainInfoScreenHeight = 0.68f
            iconRotateDegrees = 0f
            showAllTracks = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.32f)
        ) {
            AsyncImage(
                model = artistObject.image,
                contentDescription = "playlist image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent, Color.Transparent,
                                colorScheme.background
                            )
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                colorScheme.background.copy(0.33f),
                                Color.Transparent,
                            )
                        )
                    )
            )

            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "back",
                tint = colorScheme.inversePrimary,
                modifier = Modifier
                    .padding(start = 12.sdp, top = 22.sdp)
                    .rotate(animateFloatAsState(targetValue = iconRotateDegrees).value)
                    .size(28.sdp)
                    .clickable {
                        if (showAllTracks) {
                            showAllTracks = false
                            innerNavController.navigateBack()
                        } else {
                            navController.navigateBack()
                        }
                    }
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(animateFloatAsState(targetValue = 1f - mainInfoScreenHeight).value)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent, Color.Transparent,
                                colorScheme.background
                            )
                        )
                    )
            ) {
                if(showAllTracks) {
                    Text(
                        text = "All Songs",
                        style = typography().headlineSmall,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 20.sdp)
                    )
                } else {
                    Text(
                        text = artistObject.name,
                        textAlign = TextAlign.Center,
                        style = typography().headlineMedium,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(20.sdp)
                    )
                }
            }

            NavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background),
                navController = innerNavController,
                startDestination = ScreensRoutes.DetailedScreens.DetailedArtistRoute.Companion.ArtistMainInfoRoute,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                composable<ScreensRoutes.DetailedScreens.DetailedArtistRoute.Companion.ArtistMainInfoRoute> {
                    ArtistMainInfoScreen(
                        navController = navController,
                        artistObject = artistObject,
                        musicControllerUiState = musicControllerUiState,
                        user = user,
                        seeMoreTracks = {
                            showAllTracks = true
                            innerNavController.navigate(ScreensRoutes.DetailedScreens.DetailedArtistRoute.Companion.ArtistMoreTracksRoute)
                        },
                        updateUser = updateUser
                    )
                }

                composable<ScreensRoutes.DetailedScreens.DetailedArtistRoute.Companion.ArtistMoreTracksRoute> {
                    MoreArtistMusicsScreen(
                        navController = navController,
                        artistObject = artistObject,
                        musicControllerUiState = musicControllerUiState
                    )
                }
            }
        }
    }
}