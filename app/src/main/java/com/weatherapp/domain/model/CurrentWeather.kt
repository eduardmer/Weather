package com.weatherapp.domain.model

data class CurrentWeather(
    val isDay: Boolean = true,
    val cityDetails: CityDetails?,
    val details: List<WeatherConditions>
)
