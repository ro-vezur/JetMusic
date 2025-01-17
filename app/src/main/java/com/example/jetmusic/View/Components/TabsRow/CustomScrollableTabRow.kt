package com.example.jetmusic.View.Components.TabsRow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.jetmusic.ui.theme.tidalGradient
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Composable
fun CustomScrollableTabRow(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<String>,
    textColor: Color,
    onClick: (index: Int) -> Unit,
) {
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        modifier = modifier,
        edgePadding = 0.sdp,
        selectedTabIndex = selectedIndex,
        divider = { },
        containerColor = Color.Transparent,
        indicator = { tabPositions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedIndex])
                    .height(2.sdp)
                    .clip(RoundedCornerShape(16.sdp))
                    .background(tidalGradient)
            )
        }
    ) {
        items.forEachIndexed { index, item ->
            val selected = selectedIndex == index

            Tab(
                modifier = Modifier
                    .width((45 + item.length * 6).sdp)
                    .clip(RoundedCornerShape(16.sdp)),
                selected = selected,
                onClick = {
                    scope.launch {
                        onClick(index)
                    }
                },
                text = {
                    Text(
                        text = item,
                        style = typography().bodyMedium,
                        color = textColor
                    )
                }
            )
        }
    }
}