package com.example.jetmusic.ViewModels.MainScreensViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.View.Screens.HomeScreen.TabsCategories.TabsHomeCategories
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicResponse
import com.example.jetmusic.data.Remote.API.WEEK_POPULARITY
import com.example.jetmusic.other.Resources.ResultResource
import com.example.jetmusic.data.usecases.api.musicAPI.music.DiscoverSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBestMusicOfWeekUseCase: DiscoverSongsUseCase,
): ViewModel() {

    private val _selectedTabIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    private val _musicOfWeek: MutableStateFlow<ResultResource<MusicResponse>> =
        MutableStateFlow(ResultResource.Loading())
    val musicOfWeek: StateFlow<ResultResource<MusicResponse>> = _musicOfWeek.asStateFlow()

    init {
        getMusicOfWeek(TabsHomeCategories.entries.first().title)
    }

   fun getMusicOfWeek(tags: String, offset: Int = 0) = viewModelScope.launch {
       flow {
           emit(ResultResource.Loading())
           val response = getBestMusicOfWeekUseCase(tags,offset,WEEK_POPULARITY)
           if(response.results.isNotEmpty()) {
               emit(ResultResource.Success(data = response))
           } else {
               emit(ResultResource.Error(message = "empty result"))
           }
       }.catch { e ->
           emit(ResultResource.Error(message = e.message.toString()))
       }.collectLatest { result ->
           _musicOfWeek.emit(result)
       }
   }

    fun setTabIndex(tabIndex: Int) = viewModelScope.launch {
        _selectedTabIndex.emit(tabIndex)
    }
}