package com.example.jetmusic.View.Components.Buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import ir.kaaveh.sdpcompose.sdp

@Composable
fun TurnBackButton(
    modifier: Modifier = Modifier,
    size: Dp,
    border: BorderStroke,
    background: Color,
    iconColor: Color,
    shape: RoundedCornerShape = CircleShape,
    turnBack: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape)
            .size(size + 10.sdp)
            .background(background)
            .border(border,shape)
            .clickable { turnBack() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(size),
            imageVector = Icons.Default.ArrowBackIosNew,
            tint = iconColor,
            contentDescription = null,
        )
    }
}