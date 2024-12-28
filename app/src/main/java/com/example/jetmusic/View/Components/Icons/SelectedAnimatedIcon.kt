package com.example.jetmusic.View.Components.Icons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SelectedAnimatedIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    color: Color,
) {

    val enterAnimation = fadeIn(
        animationSpec = tween(500)
    )
    val exitAnimation = fadeOut(
        animationSpec = tween(500)
    )

    Box(
        modifier = Modifier
    ){
        AnimatedVisibility(
            visible = isSelected,
            enter = enterAnimation,
            exit = exitAnimation,
        ) {
            Icon(
                imageVector = if (isSelected) selectedIcon else unselectedIcon,
                contentDescription = "animated icon",
                tint = animateColorAsState(targetValue = color).value,
                modifier = modifier
            )
        }

        AnimatedVisibility(
            visible = !isSelected,
            enter = enterAnimation,
            exit = exitAnimation,
        ) {
            Icon(
                imageVector = if(isSelected) selectedIcon else unselectedIcon,
                contentDescription = "animated icon",
                tint = animateColorAsState(targetValue = color).value,
                modifier = modifier
            )
        }
    }

}