package com.example.jetmusic.Helpers.Validation.Password

import com.example.jetmusic.Helpers.String.Checkers.containsNumbers
import com.example.jetmusic.Helpers.String.Checkers.containsLowerCase
import com.example.jetmusic.Helpers.String.Checkers.containsUpperCases
import com.example.jetmusic.Helpers.Validation.Result.ValidationResults

class PasswordValidation {
    companion object {
        fun validate(password: String,additionalValidators: Boolean = false): ValidationResults {
            return when {
                password.isBlank() -> ValidationResults.ERROR
                password.length < 8 -> ValidationResults.ERROR
                !password.containsLowerCase() -> ValidationResults.ERROR
                !password.containsUpperCases() -> ValidationResults.ERROR
                !password.containsNumbers() -> ValidationResults.ERROR
                additionalValidators -> ValidationResults.ERROR
                else -> ValidationResults.CORRECT
            }
        }
    }
}