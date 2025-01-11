package com.example.jetmusic.View.Components.Cards.PlaylistCards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistObject
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun PlaylistCard(
    modifier: Modifier = Modifier,
    playlistObject: DetailedPlaylistObject,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.sdp))
            .clickable { onClick() },
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.sdp))
                .background(colorScheme.secondary)
        ) {
            if(playlistObject.tracks.isNotEmpty()) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.sdp)),
                    model = playlistObject.tracks.first().image,
                    contentDescription = "image",
                    contentScale = ContentScale.FillWidth
                    )
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 3.sdp,top = 7.sdp),
            text = playlistObject.name,
            style = typography().bodyMedium,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier
                .padding(start = 3.sdp,top = 3.sdp),
            text = playlistObject.userName,
            style = typography().bodyMedium,
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
        )
    }
}