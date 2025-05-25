package com.weatherapp.domain.model

data class CurrentWeather(
    val cityDetails: CityDetails?,
    val details: List<WeatherConditions>
)
