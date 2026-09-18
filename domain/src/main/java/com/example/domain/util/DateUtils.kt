package com.example.domain.util

import java.text.SimpleDateFormat
import java.util.Locale

fun parseDateTime(date: String, hour: String): Long? {
    return try {
        val fullDate = "$date $hour"
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        sdf.parse(fullDate)?.time
    } catch (e: Exception) {
        null
    }
}
