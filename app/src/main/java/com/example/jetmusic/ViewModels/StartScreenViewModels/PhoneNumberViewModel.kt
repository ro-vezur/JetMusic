package com.example.jetmusic.ViewModels.StartScreenViewModels

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.Remote.Repositories.Auth.OtherPlatforms.PhoneNumberManager
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneNumberViewModel @Inject constructor(
    private val phoneNumberManager: PhoneNumberManager
): ViewModel() {


    fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) = viewModelScope.launch {
        phoneNumberManager.sendVerificationCode(
            number = phoneNumber,
            activity = activity,
            callbacks = callbacks,
        )
    }
}