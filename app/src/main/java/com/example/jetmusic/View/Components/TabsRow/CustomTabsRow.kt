package com.example.jetmusic.View.Components.TabsRow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Composable
fun CustomTabRow(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    pagerState: PagerState,
    selectedColor: Color,
    unselectedColor: Color,
) {
    val scope = rememberCoroutineScope()

    TabRow(
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage, divider = {},
        containerColor = Color.Transparent,
        indicator = { tabPositions ->
            if (pagerState.currentPage < tabPositions.size) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.sdp)
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(0.42f))
                    )
                }
            }
        }
    ) {
        tabs.forEachIndexed { index, title ->
            val selected = pagerState.currentPage == index

            var selectedColor1 by remember{ mutableStateOf(selectedColor) }
            var selectedColor2 by remember{ mutableStateOf(selectedColor) }

            if(selected){
                selectedColor1 = selectedColor
                selectedColor2 = selectedColor
            } else{
                selectedColor1 = unselectedColor
                selectedColor2 = unselectedColor
            }

            Tab(
                text = {
                    Text(
                        title,
                        style = typography().bodySmall,
                        color = Color.LightGray.copy(0.92f)
                    )
                },
                selectedContentColor = selectedColor,
                unselectedContentColor = unselectedColor,
                selected = selected,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                modifier = Modifier
            )
        }
    }
}