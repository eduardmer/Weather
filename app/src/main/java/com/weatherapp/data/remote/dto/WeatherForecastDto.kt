package com.weatherapp.data.remote.dto

data class WeatherForecastDto(
    val cod: Int,
    val list: List<WeatherForecastItem>
)


