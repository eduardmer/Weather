package com.weatherapp.domain.repository

import com.weatherapp.common.Resource
import com.weatherapp.domain.model.WeatherDaily
import com.weatherapp.domain.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCurrentWeather(): Flow<Resource<WeatherDaily>>

    fun getForecastWeather(): Flow<Resource<List<WeatherForecast>>>

}