package com.example.jetmusic.Firebase.Auth

import com.example.jetmusic.Resources.ResultResource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthInterface {

    suspend fun signUp(name: String, email: String, password: String): Flow<ResultResource<AuthResult>>

    suspend fun loginUser(email: String, password: String): Flow<ResultResource<AuthResult>>

}