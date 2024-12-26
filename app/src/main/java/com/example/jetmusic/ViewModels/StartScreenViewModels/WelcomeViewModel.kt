package com.example.jetmusic.ViewModels.StartScreenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.DTOs.UserDTOs.User
import com.example.jetmusic.Firebase.Collections.UsersCollectionInterface
import com.example.jetmusic.Repositories.Auth.OtherPlatforms.GoogleManager
import com.example.jetmusic.Resources.ResultResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val googleManager: GoogleManager,
    private val usersCollectionRepository: UsersCollectionInterface,
): ViewModel() {

    fun logInWithGoogle(
        onSuccess: (newUser: User) -> Unit
    ) = viewModelScope.launch {
        googleManager.logInWithGoogle().collectLatest { result ->
            when(result) {
                is ResultResource.Loading -> {}
                is ResultResource.Success -> {
                    result.data?.user?.let { googleUser ->
                        val findUser = getUser(googleUser.uid)

                        if(findUser == null) {
                            val newUser = User.fromGoogleUser(firebaseUser = googleUser)

                            usersCollectionRepository.addUser(user = newUser).collectLatest { result ->
                                when(result) {
                                    is ResultResource.Loading -> {}
                                    is ResultResource.Success -> {
                                        result.data?.let(onSuccess)
                                    }
                                    is ResultResource.Error -> {}
                                }
                            }
                        } else {
                            onSuccess(findUser)
                        }
                    }
                }
                is ResultResource.Error -> {}
            }
        }
    }

    private suspend fun getUser(id: String): User? {
        return usersCollectionRepository.getUser(id)
    }

}