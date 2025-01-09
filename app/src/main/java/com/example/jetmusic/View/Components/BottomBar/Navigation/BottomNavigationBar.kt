package com.example.jetmusic.View.Components.BottomBar.Navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetmusic.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetmusic.BOTTOM_NAVIGATION_BAR_ITEM_SIZE
import com.example.jetmusic.View.Components.Icons.SelectedAnimatedIcon
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    var selected by remember { mutableStateOf(BottomNavigationBarItems.HOME_SCREEN) }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(currentBackStackEntry) {
        val currentRoute = currentBackStackEntry?.destination?.route.toString()

        BottomNavigationBarItems.entries.forEach { item ->
            if(currentRoute.contains(item.title)) {
                selected = item
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(0.85f)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 10.sdp)
                .fillMaxWidth(0.8f)
                .height(BOTTOM_NAVIGATION_BAR_HEIGHT.sdp)
        ) {
            BottomNavigationBarItems.entries.forEach { item ->
                val isItemSelected = selected == item

                BottomNavigationItem(
                    item = item,
                    selected = isItemSelected,
                    select = {
                        if(!isItemSelected) {
                            navController.navigate(item.route)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationItem(
    item: BottomNavigationBarItems,
    selected: Boolean,
    select: () -> Unit,
) {
    val color = if(selected) Color.White else Color.LightGray.copy(0.92f)

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.sdp))
            .width((BOTTOM_NAVIGATION_BAR_ITEM_SIZE + 16).sdp)
            .fillMaxHeight(1f)
            .clickable { select() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        SelectedAnimatedIcon(
            modifier = Modifier
                .size(BOTTOM_NAVIGATION_BAR_ITEM_SIZE.sdp),
            isSelected = selected,
            selectedIcon = item.selectedIcon,
            unselectedIcon = item.unselectedIcon,
            color = color
        )

        Text(
            modifier = Modifier
                .padding(top = 2.sdp),
            text = item.title,
            style = typography().bodySmall,
            color = animateColorAsState(targetValue = color).value,
        )
    }
}