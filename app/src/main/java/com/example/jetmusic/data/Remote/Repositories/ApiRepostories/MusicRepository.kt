package com.example.jetmusic.data.Remote.Repositories.ApiRepostories

import com.example.jetmusic.data.Remote.API.ApiService
import com.example.jetmusic.Resources.ResultResource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getBestMusicOfWeek(
        tags: String,
        offset: Int,
    ) = flow {
        emit(ResultResource.Loading())
        val response = apiService.bestMusicsOfWeek(tags,offset)
        if(response.results.isNotEmpty()) {
            emit(ResultResource.Success(data = response))
        } else {
            emit(ResultResource.Error(message = "empty result"))
        }
    }.catch { e ->
        emit(ResultResource.Error(message = e.message.toString()))
    }

}