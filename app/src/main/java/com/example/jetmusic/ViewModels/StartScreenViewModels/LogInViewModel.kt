package com.example.jetmusic.ViewModels.StartScreenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.domain.collections.UsersCollectionInterface
import com.example.jetmusic.domain.auth.FirebaseAuthInterface
import com.example.jetmusic.Helpers.Validation.Email.EmailValidation
import com.example.jetmusic.Helpers.Validation.Password.PasswordValidation
import com.example.jetmusic.Helpers.Validation.Result.ValidationResults
import com.example.jetmusic.other.Resources.ResultResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val authRepository: FirebaseAuthInterface,
    private val usersCollectionRepository: UsersCollectionInterface,
): ViewModel() {

    private val _emailValidation: MutableStateFlow<ValidationResults> = MutableStateFlow(ValidationResults.NONE)
    val emailValidation: StateFlow<ValidationResults> = _emailValidation.asStateFlow()

    private val _passwordValidation: MutableStateFlow<ValidationResults> = MutableStateFlow(ValidationResults.NONE)
    val passwordValidation: StateFlow<ValidationResults> = _passwordValidation.asStateFlow()

    suspend fun isValid(
        email: String,
        password: String,
    ): Boolean {
        val validateEmail = EmailValidation.validate(
            email = email,
            additionalValidators = !checkIsEmailRegistered(email) || !checkIsCustomProviderUsed(email)
        )
        val validatePassword = PasswordValidation.validate(
            password = password,
            additionalValidators = !checkIsPasswordMatches(email,password)
        )

        setEmailResult(validateEmail)
        setPasswordResult(validatePassword)

        return validateEmail == ValidationResults.CORRECT &&
                validatePassword == ValidationResults.CORRECT
    }

    fun logIn(
        email: String,
        password: String,
        onSuccess: (newUser: User) -> Unit,
    ) = viewModelScope.launch {
        authRepository.loginUser(
            email = email,
            password = password
        ).collectLatest { result ->
            when(result) {
                is ResultResource.Loading -> {}
                is ResultResource.Success -> {
                    result.data?.user?.let { firebaseUser ->
                        getUser(firebaseUser.uid)?.let(onSuccess)
                    }
                }
                is ResultResource.Error -> {}
            }
        }
    }

    fun setEmailResult(result: ValidationResults) = viewModelScope.launch {
        _emailValidation.emit(result)
    }

    fun setPasswordResult(result: ValidationResults) = viewModelScope.launch {
        _passwordValidation.emit(result)
    }

    private suspend fun getUser(id: String): User? {
        return usersCollectionRepository.getUser(id)
    }

    private suspend fun checkIsEmailRegistered(email: String): Boolean {
        return usersCollectionRepository.checkIsEmailRegistered(email)
    }

    private suspend fun checkIsPasswordMatches(email: String, password: String): Boolean {
        return usersCollectionRepository.checkIsPasswordMatches(email, password)
    }

    private suspend fun checkIsCustomProviderUsed(email: String) : Boolean {
        return usersCollectionRepository.checkIsCustomProviderUsed(email)
    }
}