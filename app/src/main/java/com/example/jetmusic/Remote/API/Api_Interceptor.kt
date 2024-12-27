package com.example.jetmusic.Remote.API

import com.example.jetmusic.RESPONSE_FORMAT
import okhttp3.Interceptor
import okhttp3.Response

class Api_Interceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("client_id", apiKey)
            .addQueryParameter("format", RESPONSE_FORMAT)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(newRequest)
    }
}