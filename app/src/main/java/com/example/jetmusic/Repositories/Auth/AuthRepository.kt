package com.example.jetmusic.Repositories.Auth

import com.example.jetmusic.Firebase.Auth.FirebaseAuthInterface
import com.example.jetmusic.Resources.ResultResource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
): FirebaseAuthInterface {
    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Flow<ResultResource<AuthResult>> = flow {
        emit(ResultResource.Loading())

        val result = auth.createUserWithEmailAndPassword(email,password).await()

        result.user?.let { user ->

            val profileChangeRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            user.updateProfile(profileChangeRequest)
        }

        emit(ResultResource.Success(data = result))

    }.catch() {
        emit(ResultResource.Error(message = it.message.toString()))
    }


    override suspend fun loginUser(
        email: String,
        password: String
    ): Flow<ResultResource<AuthResult>> = flow {

        emit(ResultResource.Loading())

        val result = auth.signInWithEmailAndPassword(email,password).await()

        emit(ResultResource.Success(data = result))
    }.catch {
        emit(ResultResource.Error(message = it.message.toString()))
    }
}