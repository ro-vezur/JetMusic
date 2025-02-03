package com.example.jetmusic.Hilt.Providers.api

import com.example.jetmusic.data.Remote.API.ApiService
import com.example.jetmusic.data.usecases.api.musicAPI.artist.ArtistByIdUseCase
import com.example.jetmusic.data.usecases.api.musicAPI.artist.SearchArtistsUseCase
import com.example.jetmusic.data.usecases.api.musicAPI.artist.TrendingArtistsUseCase
import com.example.jetmusic.data.usecases.api.musicAPI.music.DiscoverSongsUseCase
import com.example.jetmusic.data.usecases.api.musicAPI.music.MusicByIdUseCase
import com.example.jetmusic.data.usecases.api.musicAPI.music.SearchMusicUseCase
import com.example.jetmusic.data.usecases.api.musicAPI.playlist.PlaylistByIdUseCase
import com.example.jetmusic.data.usecases.api.musicAPI.playlist.SearchPlaylistsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideApiUseCases {

    @Provides
    @Singleton
    fun provideMusicByIdUseCase(apiService: ApiService) = MusicByIdUseCase(apiService)

    @Provides
    @Singleton
    fun provideSearchMusicUseCase(apiService: ApiService) = SearchMusicUseCase(apiService)

    @Provides
    @Singleton
    fun provideBestMusicOfWeek(apiService: ApiService) = DiscoverSongsUseCase(apiService)

    @Provides
    @Singleton
    fun provideArtistByIdUseCase(apiService: ApiService) = ArtistByIdUseCase(apiService)

    @Provides
    @Singleton
    fun provideSearchArtistsUseCase(apiService: ApiService) = SearchArtistsUseCase(apiService)

    @Provides
    @Singleton
    fun provideTrendingArtistsUseCase(apiService: ApiService) = TrendingArtistsUseCase(apiService)

    @Provides
    @Singleton
    fun providePlaylistById(apiService: ApiService) = PlaylistByIdUseCase(apiService)

    @Provides
    @Singleton
    fun provideSearchPlaylists(apiService: ApiService) = SearchPlaylistsUseCase(apiService)
}