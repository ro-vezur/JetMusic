package com.example.jetmusic.Firebase.Collections

import com.example.jetmusic.DTOs.UserDTOs.User
import com.example.jetmusic.Resources.ResultResource
import kotlinx.coroutines.flow.Flow

interface UsersCollectionInterface {

    suspend fun addUser(user: User): Flow<ResultResource<User>>

    suspend fun getUser(id: String): User?

    suspend fun checkIsEmailRegistered(email: String): Boolean

    suspend fun checkIsPasswordMatches(email: String,password: String): Boolean

    suspend fun checkIsCustomProviderUsed(email: String): Boolean
}