package com.example.jetmusic.data.Remote.API

import com.example.jetmusic.data.DTOs.API.ArtistDTOs.ArtistResponse
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicResponse
import com.example.jetmusic.data.DTOs.API.PlaylistDTOs.PlaylistResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("tracks/?")
    suspend fun musicById(
        @Query("id") id: String,
    ): MusicResponse

    @GET("tracks/?order=popularity_week")
    suspend fun bestMusicsOfWeek(
        @Query("tags") tag: String,
        @Query("offset") offset: Int,
    ): MusicResponse

    @GET("artists/?limit=20")
    suspend fun trendingArtists(): ArtistResponse

    @GET("artists/?")
    suspend fun searchArtists(
        @Query("search") search: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 5
    ): ArtistResponse

    @GET("tracks/?")
    suspend fun searchTracks(
        @Query("search") search: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 5,
    ): MusicResponse

    @GET("playlists/?")
    suspend fun searchPlaylists(
        @Query("search") search: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 5,
    ): PlaylistResponse
}