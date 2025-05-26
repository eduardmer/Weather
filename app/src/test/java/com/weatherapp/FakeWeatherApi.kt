package com.weatherapp

import com.weatherapp.data.remote.WeatherApi
import com.weatherapp.data.remote.dto.CurrentWeatherDto
import com.weatherapp.data.remote.dto.WeatherForecastDto
import java.io.IOException

class FakeWeatherApi(
    var currentWeatherShouldThrow: Boolean = false,
    var forecastWeatherShouldThrow: Boolean = false
) : WeatherApi {

    override suspend fun getCurrentWeather(
        lat: Double,
        lng: Double,
        appid: String
    ): CurrentWeatherDto {
        if (currentWeatherShouldThrow) throw IOException("Network error")
        return FakeData.currentWeatherDto
    }

    override suspend fun getForecastWeather(
        lat: Double,
        lng: Double,
        appid: String
    ): WeatherForecastDto {
        if (forecastWeatherShouldThrow) throw IOException("Network error")
        return FakeData.forecastDto
    }

}