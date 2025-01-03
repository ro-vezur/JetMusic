package com.example.jetmusic.View.Screens.SearchScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.example.jetmusic.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.example.jetmusic.data.DTOs.API.UnifiedData.UnifiedData
import com.example.jetmusic.data.DTOs.API.UnifiedData.UnifiedDataTypes
import com.example.jetmusic.View.Components.Cards.UnifiedDataCards.UnifiedDataCard
import ir.kaaveh.sdpcompose.sdp

@Composable
fun SearchedMediaScreen(
    modifier: Modifier = Modifier,
    paginatedSearchedData: LazyPagingItems<UnifiedData>,
    navigateToSelectedMusic: (id: String) -> Unit,
    navigateToSelectedPlaylist: (id: String) -> Unit,
) {
    LazyColumn(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.sdp),
    ) {
        item { }

        items(
            paginatedSearchedData.itemCount,
            key = {it}
        ) { index ->
            paginatedSearchedData[index]?.let { unifiedData ->
                UnifiedDataCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.sdp)
                        .clip(RoundedCornerShape(8.sdp))
                        .clickable {
                            when(unifiedData.type) {
                                UnifiedDataTypes.MUSIC -> { navigateToSelectedMusic(unifiedData.id) }
                                UnifiedDataTypes.ARTIST -> { }
                                UnifiedDataTypes.PLAYLIST -> { navigateToSelectedPlaylist(unifiedData.id) }
                            }
                        },
                    unifiedData = unifiedData
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(bottom = BOTTOM_NAVIGATION_BAR_HEIGHT.sdp))
        }
    }
}