package com.example.jetmusic.View.Screens.DetailedScreens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.Extensions.NavigateExtensions.navigateBack
import com.example.jetmusic.ViewModels.DetailedScreensViewModels.MusicDetailedViewModel
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.jetmusic.data.Services.MusicService

@Composable
fun MusicDetailedScreen(
    navController: NavController,
    musicObject: MusicObject,
    musicDetailedViewModel: MusicDetailedViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val currentAudioId by musicDetailedViewModel.currentAudioId.collectAsStateWithLifecycle()

    var isPlaying by remember{ mutableStateOf(musicObject.id == currentAudioId) }



    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(start = 14.sdp, top = 20.sdp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "arrow down",
                modifier = Modifier
                    .size(42.sdp)
                    .clip(RoundedCornerShape(10.sdp))
                    .clickable { navController.navigateBack() }
            )
        }

        AsyncImage(
            model = musicObject.image,
            contentDescription = "music image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 25.sdp)
                .size(250.sdp)
                .clip(RoundedCornerShape(15.sdp))
        )

        Row(
            modifier = Modifier
                .padding(start = 26.sdp, end = 26.sdp, top = 16.sdp)
                .fillMaxWidth()
        ){
            Column(
            ) {
                Text(
                    text = musicObject.name,
                    style = typography().headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    modifier = Modifier
                        .padding(top = 6.sdp),
                    text = musicObject.artist_name,
                    style = typography().titleMedium,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 28.sdp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(46.sdp)
                    .clip(CircleShape)
                    .background(colorScheme.inversePrimary)
                    .clickable {
                        if (isPlaying) {
                           val intent = Intent(context, MusicService::class.java).apply {
                               action = "START"
                               putExtra("MUSIC_URL",musicObject.audio)
                           }

                            context.startActivity(intent)
                        } else {
                            val intent = Intent(context, MusicService::class.java).apply { action = "PAUSE" }
                            context.startActivity(intent)
                        }

                        isPlaying = !isPlaying
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if(isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = "play/pause",
                    tint = colorScheme.background,
                    modifier = Modifier
                        .size(33.sdp)
                )
            }
        }
    }
}