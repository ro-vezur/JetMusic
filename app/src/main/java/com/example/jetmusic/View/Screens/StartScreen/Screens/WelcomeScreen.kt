package com.example.jetmusic.View.Screens.StartScreen.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetmusic.R
import com.example.jetmusic.View.Components.Buttons.TextButton
import com.example.jetmusic.View.ScreensRoutes
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun WelcomeScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(top = 75.sdp),
            text = "JetMusic",
            style = typography().headlineMedium
        )

        Text(
            modifier = Modifier
                .padding(top = 38.sdp),
            text = "Just keep on vibin'",
            style = typography().titleMedium,
            color = Color.LightGray.copy(0.85f)
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .padding(bottom = 15.sdp),
            verticalArrangement = Arrangement.spacedBy(14.sdp)
        ) {
            TextButton(
                modifier = Modifier,
                text = "Sign Up",
                onClick = { navController.navigate(ScreensRoutes.StartScreens.SignUpRoute)},
            )

            TextButton(
                modifier = Modifier,
                text = "Continue With Phone Number",
                onClick = {},
                background = Color.Transparent,
                textColor = Color.White,
                style = typography().bodyMedium.copy(
                    letterSpacing = 1.sp
                ),
                leadingIcon = { modifier ->
                    Icon(
                        modifier = modifier
                            .padding(start = 12.sdp)
                            .size(26.sdp),
                        imageVector = Icons.Default.PhoneAndroid,
                        contentDescription = "phone",
                    )
                }
            )

            TextButton(
                modifier = Modifier,
                text = "Continue With Google",
                onClick = {},
                background = Color.Transparent,
                textColor = Color.White,
                leadingIcon = { modifier ->
                    Image(
                        modifier = modifier
                            .padding(start = 14.sdp)
                            .size(27.sdp),
                        painter = painterResource(id = R.drawable.google__g__logo_svg),
                        contentDescription = "phone",
                    )
                }
            )

            TextButton(
                modifier = Modifier,
                text = "Log In",
                onClick = { navController.navigate(ScreensRoutes.StartScreens.LogInRoute)},
                background = Color.Transparent,
                textColor = Color.White,
                borderStroke = BorderStroke(0.sdp,Color.Transparent),
            )
        }
    }
}