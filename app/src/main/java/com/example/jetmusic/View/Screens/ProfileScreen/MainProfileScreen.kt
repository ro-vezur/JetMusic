package com.example.jetmusic.View.Screens.ProfileScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.jetmusic.View.Components.Buttons.TextButton
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.ui.theme.tidalGradient
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.jetmusic.View.ScreensRoutes

@Composable
fun MainProfileScreen(
    navController: NavController,
    user: User,
    logOut: () -> Unit,
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.sdp)
                    .height(70.sdp)
                    .background(colorScheme.background),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "My Profile",
                    style = typography().headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.weight(1f))

                TextButton(
                    modifier = Modifier,
                    text = "Edit",
                    style = typography().bodyMedium,
                    width = 75.sdp,
                    height = 24.sdp,
                    onClick = {
                        navController.navigate(ScreensRoutes.LibraryNavigationGraph.EditProfileRoute)
                    },
                    leadingIcon = { modifier ->
                        Icon(
                            modifier = modifier
                                .padding(start = 8.sdp)
                                .size(18.sdp),
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "edit",
                            tint = colorScheme.background
                            )
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 16.sdp,
                    end = 16.sdp,
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 8.sdp)
                    .size(76.sdp)
                    .border(BorderStroke(1.sdp, tidalGradient), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier
                        .size(58.sdp),
                    imageVector = Icons.Filled.Person,
                    contentDescription = "user icon"
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 10.sdp),
                text = user.fullName,
                style = typography().titleSmall,
            )

            Column(
                modifier = Modifier
                    .padding(top = 25.sdp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(7.sdp)
            ) {
                Text(
                    modifier = Modifier,
                    text = "Email",
                    style = typography().titleSmall,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    modifier = Modifier,
                    text = user.email,
                    style = typography().bodyMedium,
                    color = Color.Gray
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 20.sdp),
                horizontalArrangement = Arrangement.spacedBy(10.sdp)
            ) {
                NavigateTileCard(
                    modifier = Modifier
                        .weight(1f),
                    icon = Icons.Filled.FavoriteBorder,
                    shortInfo = "${user.likedSongsIds.size} songs",
                    onClick = {
                        navController.navigate(ScreensRoutes.LibraryNavigationGraph.LikedSongsRoute)
                    }
                )

                NavigateTileCard(
                    modifier = Modifier
                        .weight(1f),
                    icon = Icons.Filled.QueueMusic,
                    shortInfo = "${user.likedPlaylistsIds.size} playlists",
                    onClick = {
                        navController.navigate(ScreensRoutes.LibraryNavigationGraph.LikedPlaylistsRoute)
                    }
                )

                NavigateTileCard(
                    modifier = Modifier
                        .weight(1f),
                    icon = Icons.Filled.Person,
                    shortInfo = "${user.likedArtistsIds.size} artists",
                    onClick = {
                        navController.navigate(ScreensRoutes.LibraryNavigationGraph.LikedArtistsRoute)
                    }
                )
            }

            TextButton(
                modifier = Modifier
                    .padding(top = 25.sdp),
                text = "Log Out",
                onClick = { logOut() }
            )

        }
    }
}

@Composable
fun NavigateTileCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    shortInfo: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(65.sdp)
            .border(
                border = BorderStroke(1.sdp, colorScheme.secondary),
                shape = RoundedCornerShape(12.sdp)
            )
            .shadow(
                elevation = 6.sdp,
                shape = RoundedCornerShape(12.sdp),
                ambientColor = colorScheme.secondary
            )
            .background(
                Brush.verticalGradient(
                    listOf(colorScheme.secondary, colorScheme.background)
                )
            )
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 7.sdp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "icon",
                modifier = Modifier
                    .padding(top = 8.sdp)
                    .size(22.sdp)
            )

            Text(
                text = shortInfo,
                style = typography().bodyMedium,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier
                    .padding(
                        top = 6.sdp,
                        start = 1.sdp
                    )
            )
        }
    }
}
