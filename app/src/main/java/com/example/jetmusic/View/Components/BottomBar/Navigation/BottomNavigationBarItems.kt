package com.example.jetmusic.View.Components.BottomBar.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jetmusic.View.ScreenRoutes.ScreensRoutes

enum class BottomNavigationBarItems(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String,
    val route: Any
) {
    HOME_SCREEN(Icons.Filled.Home,Icons.Outlined.Home,"Home", ScreensRoutes.HomeRoute),
    SEARCH_SCREEN(Icons.Filled.Search,Icons.Outlined.Search,"Search", ScreensRoutes.SearchRoute),
    LIBRARY_SCREEN(Icons.Filled.LibraryMusic,Icons.Outlined.LibraryMusic,"Library", ScreensRoutes.LibraryRoute),
}