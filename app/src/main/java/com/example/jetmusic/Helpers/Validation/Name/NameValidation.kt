package com.example.jetmusic.Helpers.Validation.Name

import com.example.jetmusic.Helpers.String.Counters.countSpaces
import com.example.jetmusic.Helpers.Validation.ValidationResults

class NameValidation {
    companion object {
        fun validate(name: String): ValidationResults {
            return when {
                name.isBlank() -> ValidationResults.ERROR
                name.length < 3 -> ValidationResults.ERROR
                name.length > 20 -> ValidationResults.ERROR
                name.countSpaces() > 2 -> ValidationResults.ERROR
                else -> ValidationResults.CORRECT
            }
        }
    }
}