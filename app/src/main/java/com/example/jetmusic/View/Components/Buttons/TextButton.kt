package com.example.jetmusic.View.Components.Buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.example.jetmusic.ui.theme.typography
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.jetmusic.BASE_BUTTON_HEIGHT
import com.example.jetmusic.BASE_BUTTON_WIDTH
import com.example.jetmusic.ui.theme.JetMusicTheme
import ir.kaaveh.sdpcompose.sdp

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    style: TextStyle = typography().bodyLarge,
    textColor: Color = colorScheme.background.copy(0.9f),
    width: Dp = BASE_BUTTON_WIDTH.sdp,
    height: Dp = BASE_BUTTON_HEIGHT.sdp,
    corners: RoundedCornerShape = RoundedCornerShape(24.sdp),
    background: Color = colorScheme.outline,
    borderStroke: BorderStroke = BorderStroke(1.sdp, Color.White),
    leadingIcon: (@Composable (modifier: Modifier) -> Unit)? = null
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(corners)
            .background(background)
            .border(borderStroke, corners)
            .clickable { onClick() },
    ) {
        if(leadingIcon != null) {
            leadingIcon(Modifier.align(Alignment.CenterStart))
        }

        Text(
            modifier = Modifier
                .padding(
                    start = if(leadingIcon != null) 11.sdp else 5.sdp,
                    end = if(leadingIcon != null) 0.sdp else 5.sdp
                )
                .align(Alignment.Center),
            text = text,
            style = style,
            color = textColor,
            maxLines = 1
        )
    }
}
