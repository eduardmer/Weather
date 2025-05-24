package com.weatherapp.domain.model

data class CityDetails(
    val name: String,
    val temp: String,
    val description: String?,
    val tempMax: String,
    val tempMin: String
)
