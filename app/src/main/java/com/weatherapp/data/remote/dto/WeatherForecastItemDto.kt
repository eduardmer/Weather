package com.weatherapp.data.remote.dto

import com.weatherapp.common.Constants
import com.weatherapp.common.DateTimeUtils
import com.weatherapp.common.toCelsius
import com.weatherapp.data.local.entity.WeatherForecastEntity
import com.weatherapp.domain.model.WeatherForecast

data class WeatherForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>
)

fun WeatherForecastItem.toDomainModel() = WeatherForecast(
    DateTimeUtils.getDate(dt.toLong(), DateTimeUtils.TIME_FORMAT_24H),
    "${Constants.ICON_BASE_URL}${weather.firstOrNull()?.icon.orEmpty()}.png",
    main.temp.toCelsius()
)

fun WeatherForecastItem.toEntityModel() = WeatherForecastEntity(
    dt.toLong(),
    DateTimeUtils.getDate(dt.toLong(), DateTimeUtils.TIME_FORMAT_24H),
    "${Constants.ICON_BASE_URL}${weather.firstOrNull()?.icon.orEmpty()}.png",
    main.temp.toCelsius()
)
