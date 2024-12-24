package com.example.jetmusic.View.Components.BottomNaviationBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jetmusic.View.ScreensRoutes

enum class BottomNavigationBarItems(val icon: ImageVector,val title: String,val route: Any) {
    HOME_SCREEN(Icons.Filled.Home,"Home",ScreensRoutes.HomeRoute),
    SEARCH_SCREEN(Icons.Filled.Search,"Search",ScreensRoutes.SearchRoute),
    LIBRARY_SCREEN(Icons.Filled.List,"Library",ScreensRoutes.NowPlayingRoute),
}