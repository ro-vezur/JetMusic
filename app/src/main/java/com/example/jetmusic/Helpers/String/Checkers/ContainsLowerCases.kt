package com.example.jetmusic.Helpers.String.Checkers

fun String.containsLowerCase(): Boolean {
    return this.any { it.isLowerCase() }
}