package com.example.jetmusic.Helpers

import android.net.Uri
import android.util.Log

class MusicHelper {
    companion object {
        fun getTrackIdFromUrl(url: String?): String {
            return try {
                val uri = Uri.parse(url)
                uri.getQueryParameter("trackid").toString()
            } catch (e: Exception) {
                Log.e("get track id error",e.message.toString())
                ""
            }
        }
    }
}