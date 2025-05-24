package com.weatherapp.domain.model

data class Weather(
    val cityDetails: CityDetails?,
    val conditions: List<WeatherConditions>,
    val forecast: List<WeatherForecast>
)
