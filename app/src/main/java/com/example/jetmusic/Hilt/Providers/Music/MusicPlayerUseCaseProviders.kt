package com.example.jetmusic.Hilt.Providers.Music

import com.example.jetmusic.domain.service.MusicController
import com.example.jetmusic.data.usecases.musicController.DestroyMediaControllerUseCase
import com.example.jetmusic.data.usecases.musicController.SetMediaControllerCallbackUseCase
import com.example.jetmusic.data.usecases.musicController.music.ClearMediaItemsUseCase
import com.example.jetmusic.data.usecases.musicController.music.SetMediaItemsUseCase
import com.example.jetmusic.data.usecases.musicController.music.GetCurrentMusicPositionUseCase
import com.example.jetmusic.data.usecases.musicController.music.PauseMusicUseCase
import com.example.jetmusic.data.usecases.musicController.music.PlayMusicUseCase
import com.example.jetmusic.data.usecases.musicController.music.ResumeMusicUseCase
import com.example.jetmusic.data.usecases.musicController.music.SeekMusicToPositionUseCase
import com.example.jetmusic.data.usecases.musicController.music.SetMediaItemUseCase
import com.example.jetmusic.data.usecases.musicController.music.SkipToNextMusicUseCase
import com.example.jetmusic.data.usecases.musicController.music.SkipToPreviousMusicUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicPlayerUseCaseProviders {

    @Provides
    @Singleton
    fun provideAddMediaItemUseCase(musicController: MusicController) =
        SetMediaItemUseCase(musicController)

    @Provides
    @Singleton
    fun provideAddMediaItemsUseCase(musicController: MusicController) =
        SetMediaItemsUseCase(musicController)

    @Provides
    @Singleton
    fun provideGetCurrentMusicPositionUseCase(musicController: MusicController) =
        GetCurrentMusicPositionUseCase(musicController)

    @Provides
    @Singleton
    fun providePauseMusicUseCase(musicController: MusicController) =
        PauseMusicUseCase(musicController)

    @Provides
    @Singleton
    fun providePlayMusicUseCase(musicController: MusicController) =
        PlayMusicUseCase(musicController)

    @Provides
    @Singleton
    fun provideResumeMusicUseCase(musicController: MusicController) =
        ResumeMusicUseCase(musicController)

    @Provides
    @Singleton
    fun provideSeekMusicToPositionUseCase(musicController: MusicController) =
        SeekMusicToPositionUseCase(musicController)

    @Provides
    @Singleton
    fun provideSkipToNextMusicUseCase(musicController: MusicController) =
        SkipToNextMusicUseCase(musicController)

    @Provides
    @Singleton
    fun provideSkipToPreviousMusicUseCase(musicController: MusicController) =
        SkipToPreviousMusicUseCase(musicController)

    @Provides
    @Singleton
    fun provideDestroyMediaControllerUseCase(musicController: MusicController) =
        DestroyMediaControllerUseCase(musicController)

    @Provides
    @Singleton
    fun provideSetMediaControllerCallbackUseCase(musicController: MusicController) =
        SetMediaControllerCallbackUseCase(musicController)

    @Provides
    @Singleton
    fun provideClearMediaItemsUseCase(musicController: MusicController) =
        ClearMediaItemsUseCase(musicController)

}