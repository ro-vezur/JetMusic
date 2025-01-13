package com.example.jetmusic.Helpers.Pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicResponse
import com.example.jetmusic.data.DTOs.API.UnifiedData.UnifiedData

class SongsPagingSource(
    val getMusicResponse: suspend (page: Int) -> MusicResponse,
): PagingSource<Int, MusicObject>() {
    override fun getRefreshKey(state: PagingState<Int, MusicObject>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MusicObject> {

        return try {
            val page = params.key ?: 1

            val musicResponse = getMusicResponse(page)

            LoadResult.Page(
                data = musicResponse.results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (musicResponse.results.isEmpty()) null else page + 1
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}