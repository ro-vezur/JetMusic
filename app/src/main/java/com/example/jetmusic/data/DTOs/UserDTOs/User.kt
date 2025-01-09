package com.example.jetmusic.data.DTOs.UserDTOs

import com.google.firebase.auth.FirebaseUser

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val password: String,
    val customProviderUsed: Boolean,
    val likedSongsIds: MutableList<String> = mutableListOf(),
    val likedPlaylistsIds: MutableList<String> = mutableListOf(),
    val likedArtistsIds: MutableList<String> = mutableListOf(),
) {

    constructor(): this("","","","",false)

    companion object {
        fun fromGoogleUser(firebaseUser: FirebaseUser): User {
            return User(
                id = firebaseUser.uid,
                fullName = firebaseUser.displayName.toString(),
                email = firebaseUser.email.toString(),
                password = "None",
                customProviderUsed = false
            )
        }
    }
}
