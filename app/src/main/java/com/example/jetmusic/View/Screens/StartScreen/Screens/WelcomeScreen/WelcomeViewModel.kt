package com.example.jetmusic.View.Screens.StartScreen.Screens.WelcomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.domain.collections.UsersCollectionInterface
import com.example.jetmusic.data.Remote.Repositories.Auth.OtherPlatforms.GoogleManager
import com.example.jetmusic.other.Resources.ResultResource
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
                        getUser(googleUser.uid).collectLatest { findUser ->
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
                }
                is ResultResource.Error -> {}
            }
        }
    }

    private fun getUser(id: String) = usersCollectionRepository.getUserFlow(id)


}