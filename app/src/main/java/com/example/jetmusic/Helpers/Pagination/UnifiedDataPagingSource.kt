package com.example.jetmusic.Helpers.Pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.ArtistResponse
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicResponse
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Simplified.SimplifiedPlaylistResponse
import com.example.jetmusic.data.DTOs.API.UnifiedData.UnifiedData

class UnifiedDataPagingSource(
    val getMusicResponse: suspend (page: Int) -> MusicResponse,
    val getArtistsResponse: suspend (page: Int) -> ArtistResponse,
    val getPlaylistResponse: suspend (page: Int) -> SimplifiedPlaylistResponse,
): PagingSource<Int, UnifiedData>() {

    override fun getRefreshKey(state: PagingState<Int, UnifiedData>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, UnifiedData> {

        return try {
            val page = params.key ?: 1

            val musicResponse = getMusicResponse(page)
            val artistsResponse = getArtistsResponse(page)
            val playlistResponse = getPlaylistResponse(page)

            val unifiedMediaList = mutableListOf<UnifiedData>()

            unifiedMediaList.addAll(musicResponse.results.map { UnifiedData.fromMusic(it) })
            unifiedMediaList.addAll(artistsResponse.results.map { UnifiedData.fromArtist(it) })
            unifiedMediaList.addAll(playlistResponse.results.map { UnifiedData.fromPlaylist(it) })

            LoadResult.Page(
                data = unifiedMediaList,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (unifiedMediaList.isEmpty()) null else page + 1
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}