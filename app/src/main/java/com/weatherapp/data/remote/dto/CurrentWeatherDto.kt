package com.weatherapp.data.remote.dto

import com.weatherapp.common.DateTimeUtils
import com.weatherapp.common.toCelsius
import com.weatherapp.data.local.entity.WeatherConditionsEntity
import com.weatherapp.domain.model.CityDetails
import com.weatherapp.domain.model.WeatherDaily
import com.weatherapp.domain.model.WeatherConditionsType
import com.weatherapp.domain.model.WeatherConditions

data class CurrentWeatherDto(
    val cod: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)

fun CurrentWeatherDto.toDomainModel() = WeatherDaily(
    CityDetails(
        name,
        main.temp.toCelsius(),
        weather.firstOrNull()?.description,
        main.temp_max.toCelsius(),
        main.temp_min.toCelsius()
    ),
    listOf(
        WeatherConditions(
            WeatherConditionsType.SUNRISE,
            DateTimeUtils.getDate(sys.sunrise.toLong(), DateTimeUtils.TIME_FORMAT_24H),
            "Sunset: ${DateTimeUtils.getDate(sys.sunset.toLong(), DateTimeUtils.TIME_FORMAT_24H)}"
        ),
        WeatherConditions(
            WeatherConditionsType.WIND,
            "${wind.speed}",
            "Degree: ${wind.deg}"
        ),
        WeatherConditions(
            WeatherConditionsType.HUMIDITY,
            "${main.humidity}%",
            ""
        ),
        WeatherConditions(
            WeatherConditionsType.RAINFALL,
            "0 in the last 24 hours",
            ""
        ),
        WeatherConditions(
            WeatherConditionsType.FEELS_LIKE,
            main.feels_like.toCelsius(),
            ""
        ),
        WeatherConditions(
            WeatherConditionsType.PRESSURE,
            "${main.pressure}",
            ""
        )
    )
)

fun CurrentWeatherDto.toEntityModel() = listOf(
    WeatherConditionsEntity(
        WeatherConditionsType.SUNRISE,
        DateTimeUtils.getDate(sys.sunrise.toLong(), DateTimeUtils.TIME_FORMAT_24H),
        "Sunset: ${DateTimeUtils.getDate(sys.sunset.toLong(), DateTimeUtils.TIME_FORMAT_24H)}"
    ),
    WeatherConditionsEntity(
        WeatherConditionsType.WIND,
        "${wind.speed}",
        "Degree: ${wind.deg}"
    ),
    WeatherConditionsEntity(
        WeatherConditionsType.HUMIDITY,
        "${main.humidity}%",
        ""
    ),
    WeatherConditionsEntity(
        WeatherConditionsType.RAINFALL,
        "0 in the last 24 hours",
        ""
    ),
    WeatherConditionsEntity(
        WeatherConditionsType.FEELS_LIKE,
        main.feels_like.toCelsius(),
        ""
    ),
    WeatherConditionsEntity(
        WeatherConditionsType.PRESSURE,
        "${main.pressure}",
        ""
    )
)