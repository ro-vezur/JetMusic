package com.example.jetmusic.View.Components.Cards.ArtistCards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.example.jetmusic.R
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
        if(artistImage.isNotBlank()) {
            AsyncImage(
                model = artistImage,
                contentDescription = "artist image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ){
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.ic_empty_profile),
                    contentDescription = "empty artist image",
                    contentScale = ContentScale.FillWidth
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(top = 7.sdp),
            text = artistName,
            style = textStyle,
            textAlign = TextAlign.Center
        )
    }
}