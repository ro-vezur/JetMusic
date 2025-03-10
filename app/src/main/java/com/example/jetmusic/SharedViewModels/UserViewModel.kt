package com.example.jetmusic.SharedViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.domain.collections.UsersCollectionInterface
import com.example.jetmusic.other.Resources.ResultResource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
            usersCollectionRepository.getUserFlow(_firebaseUser.value?.uid.toString()).collectLatest { userToSet ->
                _user.emit(userToSet)
            }
        }
    }

    fun setUser(newUser: User?) = viewModelScope.launch {
        _user.emit(newUser)
        _firebaseUser.emit(auth.currentUser)
    }

    fun updateUser(newUser: User) = viewModelScope.launch {
        usersCollectionRepository.updateUser(newUser)
        setUser(newUser)
    }

    fun logOut() {
        auth.signOut()
        setUser(null)
    }
}