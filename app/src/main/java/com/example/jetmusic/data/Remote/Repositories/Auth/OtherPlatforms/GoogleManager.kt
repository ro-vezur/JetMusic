package com.example.jetmusic.data.Remote.Repositories.Auth.OtherPlatforms

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.jetmusic.R
import com.example.jetmusic.Resources.ResultResource
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class GoogleManager @Inject constructor(
    @ApplicationContext val context: Context,
    private val firebaseAuth: FirebaseAuth,
) {

    private fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.fold("") { str,it ->
            str + "%02x".format(it)
        }
    }

    fun logInWithGoogle(): Flow<ResultResource<AuthResult>> = flow {

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .setAutoSelectEnabled(false)
            .setNonce(createNonce())
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val credentialManager = CredentialManager.create(context)
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val credential = result.credential

            if(credential is CustomCredential) {
                if(credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        val firebaseCredential = GoogleAuthProvider
                            .getCredential(
                                googleIdTokenCredential.idToken,
                                null
                            )
                        val signInResult = firebaseAuth.signInWithCredential(firebaseCredential)

                        val authResult = signInResult.await()

                        if(signInResult.isSuccessful) {
                            emit(ResultResource.Success(data = authResult))
                        } else {
                            emit(ResultResource.Error(message = signInResult.exception?.message.toString()))
                        }


                    } catch (e: Exception) {
                        ResultResource.Error(
                            message = e.message.toString(),
                            data = null
                        )
                    }
                }
            }
        } catch (e:Exception) {
            ResultResource.Error(
                message = e.message.toString(),
                data = null
            )
        }
    }.catch {
        emit(ResultResource.Error(message = it.message.toString()))
    }
}