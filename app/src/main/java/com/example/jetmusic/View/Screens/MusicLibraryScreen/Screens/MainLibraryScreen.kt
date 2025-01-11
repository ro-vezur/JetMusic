package com.example.jetmusic.View.Screens.MusicLibraryScreen.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.example.jetmusic.View.ScreenRoutes.ScreensRoutes
import com.example.jetmusic.data.DTOs.UserDTOs.User

@Composable
fun MainLibraryScreen(
    navController: NavController,
    user: User,
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .height(70.sdp)
                    .background(colorScheme.background)
            ) {
                Text(
                    text = "Your Library",
                    style = typography().headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.sdp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(top = 6.sdp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 14.sdp),
                verticalArrangement = Arrangement.spacedBy(14.sdp),
                horizontalArrangement = Arrangement.spacedBy(12.sdp),
            ) {
                item {
                    NavigationTileCard(
                        title = "Liked Songs",
                        shortInfo = "${user.likedSongsIds.size} songs",
                        icon = Icons.Filled.FavoriteBorder,
                        onClick = {
                            navController.navigate(ScreensRoutes.LibraryNavigationGraph.LikedSongs)
                        }
                    )
                }

                item {
                    NavigationTileCard(
                        title = "Downloads",
                        shortInfo = "0 downloads",
                        icon = Icons.Filled.Download,
                        onClick = {
                            navController.navigate(ScreensRoutes.LibraryNavigationGraph.Downloads)
                        }
                    )
                }

                item {
                    NavigationTileCard(
                        title = "Playlists",
                        shortInfo = "${user.likedPlaylistsIds.size} playlists",
                        icon = Icons.Filled.QueueMusic,
                        onClick = {
                            navController.navigate(ScreensRoutes.LibraryNavigationGraph.LikedPlaylists)
                        }
                    )
                }

                item {
                    NavigationTileCard(
                        title = "Artists",
                        shortInfo = "${user.likedArtistsIds.size} artists",
                        icon = Icons.Filled.Person,
                        onClick = {
                            navController.navigate(ScreensRoutes.LibraryNavigationGraph.LikedArtists)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NavigationTileCard(
    title: String,
    shortInfo: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(120.sdp)
            .height(92.sdp)
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
                .padding(start = 14.sdp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "icon",
                modifier = Modifier
                    .padding(top = 8.sdp)
                    .size(26.sdp)
            )

            Text(
                text = title,
                style = typography().bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 6.sdp)
            )

            Text(
                text = shortInfo,
                style = typography().bodyMedium,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 5.sdp)
            )
        }
    }
}