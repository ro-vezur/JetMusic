package com.example.jetmusic.Hilt.Providers.Music

import com.example.jetmusic.domain.service.MusicController
import com.example.jetmusic.domain.usecases.musicController.DestroyMediaControllerUseCase
import com.example.jetmusic.domain.usecases.musicController.SetMediaControllerCallbackUseCase
import com.example.jetmusic.domain.usecases.musicController.music.AddMediaItemsUseCase
import com.example.jetmusic.domain.usecases.musicController.music.GetCurrentMusicPositionUseCase
import com.example.jetmusic.domain.usecases.musicController.music.PauseMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.PlayMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.ResumeMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SeekMusicToPositionUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SkipToNextMusicUseCase
import com.example.jetmusic.domain.usecases.musicController.music.SkipToPreviousMusicUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicUseCaseProviders {

    @Provides
    @Singleton
    fun provideAddMediaItemsUseCase(musicController: MusicController) =
        AddMediaItemsUseCase(musicController)

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
}