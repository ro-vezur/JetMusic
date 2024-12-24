package com.example.jetmusic.View.Components.BottomNaviationBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetmusic.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetmusic.BOTTOM_NAVIGATION_BAR_ITEM_SIZE
import com.example.jetmusic.View.ScreensRoutes
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    var selected by remember { mutableStateOf(ScreensRoutes.HomeRoute) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 10.sdp)
                .fillMaxWidth(0.8f)
                .height(BOTTOM_NAVIGATION_BAR_HEIGHT.sdp)
        ) {
            BottomNavigationBarItems.entries.forEach { item ->
                BottomNavigationItem(
                    item = item,
                    selected = selected == item.route
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationItem(
    item: BottomNavigationBarItems,
    selected: Boolean,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.sdp))
            .width((BOTTOM_NAVIGATION_BAR_ITEM_SIZE + 16).sdp)
            .fillMaxHeight(0.9f)
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = "bottom nav item",
            modifier = Modifier
                .size(BOTTOM_NAVIGATION_BAR_ITEM_SIZE.sdp)
        )

        Text(
            modifier = Modifier
                .padding(top = 1.sdp),
            text = item.title,
            style = typography().bodySmall
        )
    }
}