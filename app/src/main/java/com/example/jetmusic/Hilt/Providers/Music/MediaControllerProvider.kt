package com.example.jetmusic.Hilt.Providers.Music

import android.content.Context
import com.example.jetmusic.data.Services.MusicService.MusicControllerImpl
import com.example.jetmusic.domain.service.MusicController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaControllerProvider {
    @Singleton
    @Provides
    fun provideMusicController(@ApplicationContext context: Context): MusicController =
        MusicControllerImpl(context)
}