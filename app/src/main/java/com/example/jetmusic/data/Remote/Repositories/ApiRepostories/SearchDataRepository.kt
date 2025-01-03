package com.example.jetmusic.data.Remote.Repositories.ApiRepostories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jetmusic.Helpers.Pagination.UnifiedDataPagingSource
import com.example.jetmusic.OFFSET_PER_PAGE
import com.example.jetmusic.PAGE_SIZE
import com.example.jetmusic.data.Remote.API.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchDataRepository @Inject constructor(
    private val apiService: ApiService,
) {
    fun searchData(query: String, limit: Int = 5) = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        UnifiedDataPagingSource(
            getMusicResponse = { page -> apiService.searchTracks(query,page * OFFSET_PER_PAGE,limit) },
            getArtistsResponse = { page -> apiService.searchArtists(query,page * OFFSET_PER_PAGE,limit) },
            getPlaylistResponse = { page -> apiService.searchPlaylists(query,page * OFFSET_PER_PAGE,limit) }
        )
    }.flow

    suspend fun getMusicById(id: String) = apiService.musicById(id)

    suspend fun getPlaylistById(id: String) = apiService.playlistById(id)
}