package com.example.jetmusic.ViewModels.StartScreenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.DTOs.UserDTOs.User
import com.example.jetmusic.Firebase.Collections.UsersCollectionInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val usersCollectionRepository: UsersCollectionInterface
): ViewModel() {

    private val _firebaseUser: MutableStateFlow<FirebaseUser?> = MutableStateFlow(auth.currentUser)
    val firebaseUser: StateFlow<FirebaseUser?> = _firebaseUser.asStateFlow()

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init {
        viewModelScope.launch {
            setUser(usersCollectionRepository.getUser(_firebaseUser.value?.uid.toString()))
        }
    }

    fun setUser(newUser: User?) = viewModelScope.launch {
        _user.emit(newUser)
        _firebaseUser.emit(auth.currentUser)
    }

    fun logOut() {
        auth.signOut()
        setUser(null)
    }
}