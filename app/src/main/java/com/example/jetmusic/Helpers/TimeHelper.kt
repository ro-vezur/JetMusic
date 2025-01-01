package com.example.jetmusic.Helpers

class TimeHelper {
    companion object {
        fun formatTimeFromMillis(milliseconds: Long): String {
            val totalSeconds = milliseconds / 1000
            val minutes = totalSeconds / 60
            val remainingSeconds = totalSeconds % 60
            return String.format("%d:%02d", minutes, remainingSeconds)
        }
    }
}