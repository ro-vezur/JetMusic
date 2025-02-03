package com.example.jetmusic.ViewModels.StartScreenViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.domain.collections.UsersCollectionInterface
import com.example.jetmusic.domain.auth.FirebaseAuthInterface
import com.example.jetmusic.Helpers.Validation.Email.EmailValidation
import com.example.jetmusic.Helpers.Validation.Name.NameValidation
import com.example.jetmusic.Helpers.Validation.Password.PasswordValidation
import com.example.jetmusic.Helpers.Validation.PasswordConfirmValidation.PasswordConfirmValidation
import com.example.jetmusic.Helpers.Validation.Result.ValidationResults
import com.example.jetmusic.other.Resources.ResultResource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: FirebaseAuthInterface,
    private val usersCollectionRepository: UsersCollectionInterface
): ViewModel() {

    private val _registerResult: MutableStateFlow<ResultResource<AuthResult>> =
        MutableStateFlow(ResultResource.Loading())
    val registerResult: StateFlow<ResultResource<AuthResult>> = _registerResult.asStateFlow()

    private val _nameValidation: MutableStateFlow<ValidationResults> =
        MutableStateFlow(ValidationResults.NONE)
    val nameValidation: StateFlow<ValidationResults> = _nameValidation.asStateFlow()

    private val _emailValidation: MutableStateFlow<ValidationResults> =
        MutableStateFlow(ValidationResults.NONE)
    val emailValidation = _emailValidation.asStateFlow()

    private val _passwordValidation: MutableStateFlow<ValidationResults> =
        MutableStateFlow(ValidationResults.NONE)
    val passwordValidation: StateFlow<ValidationResults> = _passwordValidation.asStateFlow()

    private val _passwordConfirmValidation: MutableStateFlow<ValidationResults> =
        MutableStateFlow(ValidationResults.NONE)
    val passwordValidationConfirm: StateFlow<ValidationResults> = _passwordConfirmValidation.asStateFlow()

    suspend fun isValid(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Boolean {

        val validateName = NameValidation.validate(name)
        val validateEmail = EmailValidation.validate(
            email = email,
            additionalValidators = checkIsEmailRegistered(email)
        )

        val validatePassword = PasswordValidation.validate(password)
        val validatePasswordConfirm = PasswordConfirmValidation.validate(password,passwordConfirm)

        setNameResult(validateName)
        setEmailResult(validateEmail)
        setPasswordResult(validatePassword)
        setPasswordConfirmResult(validatePasswordConfirm)

        return validateName == ValidationResults.CORRECT &&
                validateEmail == ValidationResults.CORRECT &&
                validatePassword == ValidationResults.CORRECT &&
                validatePasswordConfirm == ValidationResults.CORRECT
    }

    fun signUp(
        name: String,
        email: String,
        password: String,
        onSuccess: (newUser: User) -> Unit,
    ) = viewModelScope.launch {
        authRepository.signUp(
            name = name,
            email = email,
            password = password,
        ).collectLatest { result ->
            _registerResult.emit(result)

            when(result) {
                is ResultResource.Loading -> {}
                is ResultResource.Success -> {
                    result.data?.user?.let { firebaseUser ->
                        val newUser = User(
                            id = firebaseUser.uid,
                            fullName = name,
                            email = email,
                            password = password,
                            customProviderUsed = true
                        )

                        usersCollectionRepository.addUser(user = newUser).collectLatest { result ->
                            when(result) {
                                is ResultResource.Loading -> {}
                                is ResultResource.Success -> {
                                    result.data?.let(onSuccess)
                                }
                                is ResultResource.Error -> {}
                            }
                        }
                    }
                }
                is ResultResource.Error -> {}
            }
        }
    }

    fun setNameResult(result: ValidationResults) = viewModelScope.launch {
        _nameValidation.emit(result)
    }

    fun setEmailResult(result: ValidationResults) = viewModelScope.launch {
        _emailValidation.emit(result)
    }

    fun setPasswordResult(result: ValidationResults) = viewModelScope.launch {
        _passwordValidation.emit(result)
    }

    fun setPasswordConfirmResult(result: ValidationResults) = viewModelScope.launch {
        _passwordConfirmValidation.emit(result)
    }

    private suspend fun checkIsEmailRegistered(email: String): Boolean {
        return usersCollectionRepository.checkIsEmailRegistered(email)
    }
}