package com.example.jetmusic.domain.collections

import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.other.Resources.ResultResource
import kotlinx.coroutines.flow.Flow

interface UsersCollectionInterface {

    fun addUser(user: User): Flow<ResultResource<User>>

    suspend fun updateUser(user: User)

    fun getUserFlow(id: String): Flow<User?>

    suspend fun checkIsEmailRegistered(email: String): Boolean

    suspend fun checkIsPasswordMatches(email: String,password: String): Boolean

    suspend fun checkIsCustomProviderUsed(email: String): Boolean
}