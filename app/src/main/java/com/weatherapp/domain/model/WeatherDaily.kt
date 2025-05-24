package com.weatherapp.domain.model

data class WeatherDaily(
    val cityDetails: CityDetails?,
    val details: List<WeatherConditions>
)
