package com.example.jetmusic.ViewModels.MainScreensViewModels.LibraryScreenViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.jetmusic.data.DTOs.API.ArtistDTOs.Detailed.DetailedArtistObject
import com.example.jetmusic.data.usecases.api.musicAPI.artist.ArtistByIdUseCase
import com.example.jetmusic.other.Resources.ResultResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class LikedArtistsViewModel @Inject constructor(
    private val artistByIdUseCase: ArtistByIdUseCase
): ViewModel() {

    private val _likedArtistsResult: MutableStateFlow<ResultResource<List<DetailedArtistObject>>> =
        MutableStateFlow(ResultResource.Loading())
    val likedArtistsResult: StateFlow<ResultResource<List<DetailedArtistObject>>> = _likedArtistsResult.asStateFlow()

    suspend fun setLikedArtists(newLikedArtistsIds: List<String>) = flow {
        val newLikedArtistsList = mutableListOf<DetailedArtistObject>()

        emit(ResultResource.Loading())

        if(newLikedArtistsIds.isEmpty()) {
            emit(ResultResource.Error(message = "You don't have any liked artists"))
        } else {
            newLikedArtistsIds.forEach { id ->
                val artistResponse = artistByIdUseCase(id)
                if(artistResponse.results.isNotEmpty()) {
                    newLikedArtistsList.add(artistResponse.results.first())
                    emit(ResultResource.Success(data = newLikedArtistsList.toList()))
                }
            }
        }
    }.catch { e ->
        emit(ResultResource.Error(message = e.message.toString()))
    }.collectLatest { result ->
        _likedArtistsResult.emit(result)
    }
}