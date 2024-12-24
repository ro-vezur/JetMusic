package com.example.jetmusic.ViewModels.StartScreenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmusic.Helpers.Validation.Email.Email
import com.example.jetmusic.Helpers.Validation.Name.NameValidation
import com.example.jetmusic.Helpers.Validation.Password.PasswordValidation
import com.example.jetmusic.Helpers.Validation.PasswordConfirmValidation.PasswordConfirmValidation
import com.example.jetmusic.Helpers.Validation.Result.ValidationResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

): ViewModel() {

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

    fun isValid(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Boolean {

        val nameValidation = NameValidation.validate(name)
        val emailValidation = Email.validate(email)
        val passwordValidation = PasswordValidation.validate(password)
        val passwordConfirmValidation = PasswordConfirmValidation.validate(password,passwordConfirm)

        setNameResult(nameValidation)
        setEmailResult(emailValidation)
        setPasswordResult(passwordValidation)
        setPasswordConfirmResult(passwordConfirmValidation)

        return nameValidation == ValidationResults.CORRECT &&
                emailValidation == ValidationResults.CORRECT &&
                passwordValidation == ValidationResults.CORRECT &&
                passwordConfirmValidation == ValidationResults.CORRECT
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
}