package com.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weatherapp.domain.model.WeatherForecast

@Entity
data class WeatherForecastEntity(
    @PrimaryKey
    val dateInSeconds: Long,
    val date: String,
    val icon: String,
    val temp: String
)

fun WeatherForecastEntity.toDomainModel() = WeatherForecast(
    date,
    icon,
    temp
)
