package com.example.jetmusic.data.Remote.Repositories.Database

import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.domain.collections.UsersCollectionInterface
import com.example.jetmusic.other.Resources.ResultResource
import com.example.jetmusic.USERS_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class UsersCollectionRepository(
    private val db: FirebaseFirestore
): UsersCollectionInterface {

    private val collection = db.collection(USERS_COLLECTION)

    override suspend fun addUser(user: User) = flow {
        emit(ResultResource.Loading())
        val userDocument = collection.document(user.id).get().await()

        if(!userDocument.exists()) {
            collection.document(user.id).set(user).await()
            emit(ResultResource.Success(data = user))
        } else {
            emit(ResultResource.Error(message = "User already exists"))
        }

    }.catch { e ->
        emit(ResultResource.Error(message = e.message.toString()))
    }

    override suspend fun getUser(id: String): User? {
        val userDocument = collection.document(id).get().await()

        if (userDocument.exists()) {
            return userDocument.toObject(User::class.java)
        }

        return null
    }

    override suspend fun checkIsEmailRegistered(email: String): Boolean {
        val documents = collection.get().await().documents

        val findEmail = documents.find {
            val user = it.toObject(User::class.java)
            user?.email == email && user.customProviderUsed
        }

        return findEmail != null
    }

    override suspend fun checkIsPasswordMatches(email: String, password: String): Boolean {
        val documents = collection.get().await().documents

        val passwordMatches = documents.find {
            val user = it.toObject(User::class.java)
            user?.email == email && user.password == password
        }

        return passwordMatches != null
    }

    override suspend fun checkIsCustomProviderUsed(email: String): Boolean {
        val documents = collection.get().await().documents

        val findEmail = documents.find {
            val user = it.toObject(User::class.java)
            user?.email == email && user.customProviderUsed
        }

        return findEmail != null
    }
}