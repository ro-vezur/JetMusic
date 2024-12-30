package com.example.jetmusic.View.Components.Cards.MusicCards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import com.example.jetmusic.data.enums.Genres.MusicGenres
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun MusicGenreCard(
    modifier: Modifier = Modifier,
    musicGenre: MusicGenres,
) {

    Box(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = musicGenre.image,
            contentDescription = "music genre image",
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.1f))
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.sdp)
                .clip(RoundedCornerShape(8.sdp)),
            text = musicGenre.displayName,
            style = typography().titleMedium,
            fontWeight = FontWeight.W500
        )
    }
}