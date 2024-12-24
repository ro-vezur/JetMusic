package com.example.jetmusic.Helpers.String.Counters

fun String.countSpaces(): Int {
    return this.count { it == ' ' }
}