package com.example.jetmusic.data.DTOs.API

import kotlinx.serialization.Serializable

@Serializable
data class ResponseHeaders(
    val status: String,
    val code: String,
    val error_message: String,
    val warnings: String,
    val results_count: Int
)
