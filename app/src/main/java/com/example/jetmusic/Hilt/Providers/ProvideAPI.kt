package com.example.jetmusic.Hilt.Providers

import com.example.jetmusic.API_KEY
import com.example.jetmusic.BASE_API_URL
import com.example.jetmusic.data.Remote.API.ApiService
import com.example.jetmusic.data.Remote.API.Api_Interceptor
import com.example.jetmusic.data.Remote.Repositories.ApiRepostories.ArtistsRepository
import com.example.jetmusic.data.Remote.Repositories.ApiRepostories.MusicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideAPI {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(Api_Interceptor(API_KEY))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMusicRepository(apiService: ApiService): MusicRepository {
        return MusicRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideArtistRepository(apiService: ApiService): ArtistsRepository {
        return ArtistsRepository(apiService)
    }
}