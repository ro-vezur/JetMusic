package com.example.jetmusic.data.Remote.API

import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistResponse
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Simplified.SimplifiedArtistResponse
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicResponse
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Detailed.DetailedPlaylistResponse
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.Simplified.SimplifiedPlaylistResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("tracks/?")
    suspend fun musicById(
        @Query("id") id: String,
    ): MusicResponse

    @GET("playlists/tracks?")
    suspend fun playlistById(
        @Query("id") id: String,
    ): DetailedPlaylistResponse

    @GET("artists/tracks?")
    suspend fun artistById(
        @Query("id") id: String
    ): DetailedArtistResponse

    @GET("tracks/?order=popularity_week")
    suspend fun bestMusicsOfWeek(
        @Query("tags") tags: String,
        @Query("offset") offset: Int,
        @Query("order") order: String,
    ): MusicResponse

    @GET("artists/?limit=20")
    suspend fun trendingArtists(): SimplifiedArtistResponse

    @GET("artists/?")
    suspend fun searchArtists(
        @Query("namesearch") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): SimplifiedArtistResponse

    @GET("tracks/?")
    suspend fun searchMusic(
        @Query("namesearch") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): MusicResponse

    @GET("playlists/?")
    suspend fun searchPlaylists(
        @Query("namesearch") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): SimplifiedPlaylistResponse

}