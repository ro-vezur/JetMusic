package com.example.jetmusic.Helpers.String.Checkers

fun String.containsNumbers(): Boolean {
    return this.any { it.isDigit() }
}