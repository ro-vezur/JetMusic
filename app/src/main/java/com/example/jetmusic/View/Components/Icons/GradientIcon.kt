package com.example.jetmusic.View.Components.Icons

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun GradientIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    gradient: Brush
) {
    Icon(
        modifier = modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient, blendMode = BlendMode.SrcAtop)
                }
            },
        imageVector = icon,
        contentDescription = null,
    )
}