package com.example.jetmusic.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

@Composable
fun typography():Typography {
    return Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.W500,
            fontSize = 15.ssp,
            lineHeight = 24.ssp,
            letterSpacing = 1.ssp
        ),
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 13.ssp,
            lineHeight = 24.ssp,
            letterSpacing = 1.ssp
        ),
        bodySmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 24.ssp,
            letterSpacing = 1.ssp
        ),
        headlineMedium = TextStyle(
            fontWeight = FontWeight.W500,
            fontSize = 32.ssp,
            letterSpacing = 3.ssp
        ),
        titleMedium = TextStyle(
            fontWeight = FontWeight.W500,
            fontSize = 18.ssp,
            letterSpacing = 1.ssp
        ),
        titleSmall = TextStyle(
            fontWeight = FontWeight.W500,
            fontSize = 16.ssp,
            letterSpacing = 1.ssp
        )
    )
}