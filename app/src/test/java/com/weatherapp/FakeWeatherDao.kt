package com.weatherapp

import com.weatherapp.data.local.dao.WeatherDao
import com.weatherapp.data.local.entity.WeatherConditionsEntity
import com.weatherapp.data.local.entity.WeatherForecastEntity

class FakeWeatherDao : WeatherDao {
    val conditions = mutableListOf<WeatherConditionsEntity>()
    val forecast = mutableListOf<WeatherForecastEntity>()

    override suspend fun getWeatherConditions(): List<WeatherConditionsEntity> = conditions
    override suspend fun getWeatherForecast(): List<WeatherForecastEntity> = forecast

    override suspend fun saveWeatherConditions(items: List<WeatherConditionsEntity>) {
        conditions.clear()
        conditions.addAll(items)
    }

    override suspend fun saveWeatherForecast(items: List<WeatherForecastEntity>) {
        forecast.clear()
        forecast.addAll(items)
    }

    override suspend fun clearWeatherForecast() {
        forecast.clear()
    }
}