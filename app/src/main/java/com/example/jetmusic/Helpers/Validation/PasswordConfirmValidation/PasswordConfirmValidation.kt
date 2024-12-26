package com.example.jetmusic.Helpers.Validation.PasswordConfirmValidation

import com.example.jetmusic.Helpers.Validation.Result.ValidationResults

class PasswordConfirmValidation {
    companion object {
        fun validate(password: String, passwordToConfirm: String): ValidationResults {
            return when {
                password.isBlank() -> ValidationResults.ERROR
                passwordToConfirm.isBlank() -> ValidationResults.ERROR
                passwordToConfirm != password -> ValidationResults.ERROR
                else -> ValidationResults.CORRECT
            }
        }
    }
}