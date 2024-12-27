package com.example.jetmusic.Remote.API

import com.example.jetmusic.DTOs.API.ArtistDTOs.ArtistResponse
import com.example.jetmusic.DTOs.API.MusicDTOs.MusicResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("tracks/?order=popularity_week")
    suspend fun bestMusicsOfWeek(
        @Query("tags") tag: String,
        @Query("offset") offset: Int,
    ): MusicResponse

    @GET("artists/?limit=20")
    suspend fun trendingArtists(): ArtistResponse
}