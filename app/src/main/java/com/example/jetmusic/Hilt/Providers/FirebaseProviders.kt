package com.example.jetmusic.Hilt.Providers

import android.content.Context
import com.example.jetmusic.domain.collections.UsersCollectionInterface
import com.example.jetmusic.domain.auth.FirebaseAuthInterface
import com.example.jetmusic.data.Remote.Repositories.Auth.AuthRepository
import com.example.jetmusic.data.Remote.Repositories.Auth.OtherPlatforms.GoogleManager
import com.example.jetmusic.data.Remote.Repositories.Database.UsersCollectionRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseProviders {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): FirebaseAuthInterface {
        return AuthRepository(auth)
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideUsersCollection(db: FirebaseFirestore): UsersCollectionInterface {
        return UsersCollectionRepository(db)
    }

    @Provides
    @Singleton
    fun provideGoogleManager(
        @ApplicationContext context: Context,
        auth: FirebaseAuth
    ): GoogleManager {
        return GoogleManager(context,auth)
    }
}