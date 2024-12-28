package com.example.jetmusic.data.Remote.Repositories.ApiRepostories

import com.example.jetmusic.data.Remote.API.ApiService
import com.example.jetmusic.Resources.ResultResource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTrendingArtists() = flow {
        emit(ResultResource.Loading())

        val response = apiService.trendingArtists()

        if(response.results.isNotEmpty()) {
            emit(ResultResource.Success(data = response))
        } else {
            emit(ResultResource.Error(message = "Results is empty"))
        }
    }.catch { e ->
        emit(ResultResource.Error(message = e.message.toString()))
    }
}