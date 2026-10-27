package com.example.core.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getCurrentDateTime(): Date = Calendar.getInstance().time

fun Date.toStringFormat(
    format: String,
    locale: Locale = Locale.getDefault()
): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getInitialTime(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val min = Calendar.getInstance().get(Calendar.MINUTE)
    return "$hour:$min hrs"
}

@SuppressLint("SimpleDateFormat")
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}

const val DATE_FORMAT = "dd/MM/yyyy"
