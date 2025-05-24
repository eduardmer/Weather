package com.weatherapp.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeUtils {

    const val TIME_FORMAT_24H = "HH:mm"

    fun getDate(timeInSeconds: Long, dateFormat: String): String {
        return SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date(timeInSeconds * 1000))
    }

}