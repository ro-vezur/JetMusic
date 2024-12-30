package com.example.jetmusic.View.Components.BottomBar.MusicBar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.jetmusic.data.Services.MusicService.MusicControllerUiState
import com.example.jetmusic.states.PlayerState
import ir.kaaveh.sdpcompose.sdp

@Composable
fun MusicBottomBar(
    musicControllerUiState: MusicControllerUiState,
) {

    LaunchedEffect(musicControllerUiState.currentMusic) {
        Log.d("current musik",musicControllerUiState.currentMusic.toString())
    }

    musicControllerUiState.currentMusic?.let { musicObject ->
        if(musicControllerUiState.playerState == PlayerState.PAUSED){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.sdp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = musicObject.image,
                        contentDescription = "music image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(42.sdp)
                            .clip(RoundedCornerShape(8.sdp))
                    )
                }
            }
        }
    }
}