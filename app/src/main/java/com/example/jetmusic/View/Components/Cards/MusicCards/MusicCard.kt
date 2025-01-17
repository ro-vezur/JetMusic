package com.example.jetmusic.View.Components.Cards.MusicCards

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun MusicCard(
    modifier: Modifier = Modifier,
    musicObject: MusicObject,
    trailingIcon: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(10.sdp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = musicObject.image,
                contentDescription = "music image",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .clip(RoundedCornerShape(10.sdp))
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .horizontalScroll(rememberScrollState()),
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 9.sdp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 4.sdp),
                        text = musicObject.name,
                        fontSize = typography().bodyMedium.fontSize / 1.04f,
                        maxLines = 1
                    )

                    if(musicObject.artist_name != "null" && !musicObject.artist_name.isNullOrBlank()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 2.sdp),
                            text = musicObject.artist_name,
                            fontSize = typography().bodySmall.fontSize * 1.05f,
                            color = Color.Gray,
                            maxLines = 1
                        )
                    }
                }
            }

            if (trailingIcon != null) {
                trailingIcon()
            }
        }

        if (bottomBar != null) {
            bottomBar()
        }
    }
}