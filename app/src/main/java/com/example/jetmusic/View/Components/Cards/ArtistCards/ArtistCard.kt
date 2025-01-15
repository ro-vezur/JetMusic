package com.example.jetmusic.View.Components.Cards.ArtistCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ArtistCard(
    modifier: Modifier = Modifier,
    artistImage: String,
    artistName: String,
    textStyle: TextStyle = typography().bodySmall,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AsyncImage(
            model = artistImage,
            contentDescription = "artist image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape)
        )

        Text(
            modifier = Modifier
                .padding(top = 7.sdp),
            text = artistName,
            style = textStyle,
            textAlign = TextAlign.Center
        )
    }
}