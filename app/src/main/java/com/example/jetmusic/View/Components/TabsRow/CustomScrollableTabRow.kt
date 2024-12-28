package com.example.jetmusic.View.Components.TabsRow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Composable
fun CustomScrollableTabRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    items: List<String>,
    textColor: Color,
    containerColor: Color,
    onClick: (index: Int) -> Unit,
) {
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        modifier = modifier,
        edgePadding = 0.sdp,
        selectedTabIndex = pagerState.currentPage, divider = {},
        containerColor = Color.Transparent,
        indicator = { tabPositions ->
            tabPositions.forEach {

            }
        }
    ) {
        items.forEachIndexed { index, item ->
            val selected = pagerState.currentPage == index

            val color = if(selected) containerColor else Color.Transparent

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.sdp)
                    .width(70.sdp)
                    .height(32.sdp)
                    .clip(RoundedCornerShape(16.sdp))
                    .background(color)
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                            onClick(index)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item,
                    style = typography().bodyMedium,
                    color = textColor
                )
            }
        }
    }
}