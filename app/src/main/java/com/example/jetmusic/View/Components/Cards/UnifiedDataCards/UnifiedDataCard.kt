package com.example.jetmusic.View.Components.Cards.UnifiedDataCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.jetmusic.data.DTOs.API.UnifiedData.UnifiedData
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun UnifiedDataCard(
    modifier: Modifier = Modifier,
    unifiedData: UnifiedData,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        AsyncImage(
            model = unifiedData.image,
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 12.sdp)
                .size(38.sdp)
                .clip(RoundedCornerShape(9.sdp))
        )

        Column(
            modifier = Modifier
                .padding(start = 10.sdp),
            verticalArrangement = Arrangement.spacedBy(3.sdp)
        ) {
            Text(
                text = unifiedData.name,
                style = typography().bodyMedium
            )

            Text(
                text = unifiedData.type.title,
                style = typography().bodyMedium,
                color = Color.Gray
            )
        }
    }
}