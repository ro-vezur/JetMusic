package com.example.jetmusic.other.Resources

sealed class ResultResource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResultResource<T>(data)
    class Error<T>(message: String, data: T? = null) : ResultResource<T>(data, message)
    class Loading<T>(data: T? = null) : ResultResource<T>(data)
}