package com.example.jetmusic.View.Screens.DetailedScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jetmusic.Extensions.NavigateExtensions.navigateBack
import com.example.jetmusic.ViewModels.MusicPlayerViewModel
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistObject
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistObject
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.other.events.MusicPlayerEvent
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DetailedArtistScreen(
    navController: NavController,
    artistObject: DetailedArtistObject,
    musicControllerUiState: MusicControllerUiState,
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
) {
    LaunchedEffect(null) {
        musicPlayerViewModel.onEvent(MusicPlayerEvent.PauseSong)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
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
                                MaterialTheme.colorScheme.background
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

                                MaterialTheme.colorScheme.background.copy(0.3f),
                                Color.Transparent, Color.Transparent,
                            )
                        )
                    )
            )

            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "back",
                tint = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier
                    .padding(start = 12.sdp, top = 22.sdp)
                    .size(28.sdp)
                    .clickable { navController.navigateBack() }
            )

            Text(
                text = artistObject.name,
                style = typography().headlineMedium,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(bottom = 25.sdp)
                    .align(Alignment.BottomCenter)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.68f)
        ) {

        }
    }
}