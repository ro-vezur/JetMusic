package com.example.jetmusic.Helpers.String.Checkers

fun String.containsUpperCases(): Boolean {
    return this.any { it.isUpperCase() }
}