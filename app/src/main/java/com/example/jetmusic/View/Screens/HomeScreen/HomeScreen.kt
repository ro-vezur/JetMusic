package com.example.jetmusic.View.Screens.HomeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.other.Resources.ResultResource
import com.example.jetmusic.View.Components.Cards.MusicCards.MusicCard
import com.example.jetmusic.View.Components.TabsRow.CustomScrollableTabRow
import com.example.jetmusic.View.Screens.HomeScreen.TabsCategories.TabsHomeCategories
import com.example.jetmusic.View.ScreenRoutes.ScreensRoutes
import com.example.jetmusic.ViewModels.MainScreensViewModels.HomeViewModel
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.ui.theme.tidalGradient
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun HomeScreen(
    navController: NavController,
    user: User,
    selectMusic: (MusicObject) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val selectedTabIndex by homeViewModel.selectedTabIndex.collectAsStateWithLifecycle()
    val musicOfWeek by homeViewModel.musicOfWeek.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(top = 20.sdp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 12.sdp),
                verticalArrangement = Arrangement.spacedBy(5.sdp)
            ) {
                Text(
                    text = "Hi ${user.fullName},",
                    style = typography().titleSmall,
                    fontWeight = FontWeight.W400,
                    color = Color.LightGray.copy(0.9f)
                )

                Text(
                    text = "Good evening",
                    style = typography().titleMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .padding(end = 28.sdp)
                    .size(42.sdp)
                    .clip(CircleShape)
                    .border(BorderStroke(1.sdp, tidalGradient), CircleShape)
                    .clickable { navController.navigate(ScreensRoutes.LibraryNavigationGraph.ProfileRoute) },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.sdp),
                    imageVector = Icons.Filled.Person,
                    contentDescription = "user icon"
                )
            }
        }

        CustomScrollableTabRow(
            modifier = Modifier
                .padding(top = 20.sdp, start = 6.sdp, end = 6.sdp)
                .fillMaxWidth(),
            selectedIndex = selectedTabIndex,
            items = TabsHomeCategories.entries.map { it.title },
            textColor = Color.Gray,
            onClick = { index ->
                homeViewModel.setTabIndex(index)
                homeViewModel.getMusicOfWeek(
                    tags = TabsHomeCategories.entries[index].genreId.toString(),
                )
            }
        )

        Box(
            modifier = Modifier
                .padding(top = 18.sdp)
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.78f)
                .shadow(
                    elevation = 8.sdp,
                    shape = RoundedCornerShape(14.sdp),
                )
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.DarkGray, colorScheme.background)
                    )
                )
        ) {
            when (musicOfWeek) {
                is ResultResource.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }

                is ResultResource.Success -> {
                    musicOfWeek.data?.let { musicResponse ->

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.sdp),
                            contentPadding = PaddingValues(horizontal = 10.sdp)
                        ) {
                            item {  }

                            items(
                                musicResponse.results,
                                key = { it.id }
                            ) { music ->
                                MusicCard(
                                    modifier = Modifier
                                        .height(42.sdp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.sdp))
                                        .clickable { selectMusic(music) },
                                    musicObject = music
                                )
                            }
                        }
                    }
                }

                is ResultResource.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "error")
                    }
                }
            }
        }
    }
}

