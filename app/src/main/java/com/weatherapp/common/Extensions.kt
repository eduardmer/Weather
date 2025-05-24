package com.weatherapp.common

fun Double?.toCelsius() =
    if (this != null)
        "%.1f°".format(this - 273.15)
    else ""