package com.example.jetmusic.View.Screens.ResultScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.jetmusic.ui.theme.typography

@Composable
fun ErrorScreen(modifier: Modifier = Modifier,errorText: String) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        ) {
        Text(
            text = errorText,
            style = typography().titleSmall
        )
    }
}