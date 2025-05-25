package com.weatherapp.domain.repository

import com.weatherapp.common.Resource
import com.weatherapp.domain.model.CurrentWeather
import com.weatherapp.domain.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCurrentWeather(): Flow<Resource<CurrentWeather>>

    fun getForecastWeather(): Flow<Resource<List<WeatherForecast>>>

}