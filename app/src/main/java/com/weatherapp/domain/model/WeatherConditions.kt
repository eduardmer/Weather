package com.weatherapp.domain.model

data class WeatherConditions(
    val type: WeatherConditionsType,
    val description: String,
    val additionalDatas: String
)
