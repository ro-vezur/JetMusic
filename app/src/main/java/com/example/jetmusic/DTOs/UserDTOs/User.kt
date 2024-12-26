package com.example.jetmusic.DTOs.UserDTOs

import com.google.firebase.auth.FirebaseUser

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val password: String,
    val customProviderUsed: Boolean,
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
