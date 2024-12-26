package com.example.jetmusic.Helpers.Validation.Email

import android.util.Patterns
import com.example.jetmusic.Helpers.Validation.Result.ValidationResults

class EmailValidation {
    companion object {
        fun validate(email: String,additionalValidators: Boolean = false): ValidationResults {
            return when {
                email.isEmpty() -> ValidationResults.ERROR
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResults.ERROR
                additionalValidators -> ValidationResults.ERROR
                else -> ValidationResults.CORRECT
            }
        }
    }
}