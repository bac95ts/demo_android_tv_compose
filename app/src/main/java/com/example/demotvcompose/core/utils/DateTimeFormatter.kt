package com.example.demotvcompose.core.utils

import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Utility class to format streaming time and durations
 */
object DateTimeFormatter {
    /**
     * Formats milliseconds into a standard media playback duration string: "HH:mm:ss" or "mm:ss"
     */
    fun formatMs(ms: Long): String {
        if (ms < 0) return "00:00"
        val hours = TimeUnit.MILLISECONDS.toHours(ms)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(ms) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(ms) % 60
        
        return if (hours > 0) {
            String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }
    }
}
