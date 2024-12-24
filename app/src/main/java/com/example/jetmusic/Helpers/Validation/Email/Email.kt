package com.example.jetmusic.Helpers.Validation.Email

import android.util.Patterns
import com.example.jetmusic.Helpers.Validation.Result.ValidationResults

class Email {
    companion object {
        fun validate(email: String): ValidationResults {
            return when {
                email.isEmpty() -> ValidationResults.ERROR
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResults.ERROR
                else -> ValidationResults.CORRECT
            }
        }
    }
}