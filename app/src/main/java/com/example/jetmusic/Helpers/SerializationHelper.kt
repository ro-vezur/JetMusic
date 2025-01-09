package com.example.jetmusic.Helpers

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class SerializationHelper {

    companion object {
        @OptIn(InternalSerializationApi::class)
        inline fun <reified T> encode(obj: T): String {
            return Json.encodeToString(serializer<T>(),obj)
        }

        inline fun <reified T> decode(string: String): T {
            return Json.decodeFromString(string)
        }
    }
}