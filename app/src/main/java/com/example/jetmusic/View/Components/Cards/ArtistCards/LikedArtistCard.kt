package com.example.jetmusic.View.Components.Cards.ArtistCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistObject
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun LikedArtistCard(
    modifier: Modifier = Modifier,
    artistObject: DetailedArtistObject,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AsyncImage(
            model = artistObject.image,
            contentDescription = "artist image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.sdp))
        )

        Text(
            modifier = Modifier
                .padding(top = 7.sdp),
            text = artistObject.name,
            style = typography().bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}