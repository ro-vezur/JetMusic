package com.example.jetmusic.View.Components.InputFields.ValidationTextField

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import ir.kaaveh.sdpcompose.sdp
import androidx.compose.material3.MaterialTheme.colorScheme

@Composable
fun DefaultValidationLeadingIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    tint: Color = colorScheme.inversePrimary
) {
    Icon(
        modifier = modifier
            .padding(start = 12.sdp)
            .size(24.sdp),
        imageVector = icon,
        contentDescription = "validation field icon",
        tint = tint
    )
}