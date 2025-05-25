package com.weatherapp.domain.repository

import com.weatherapp.common.Resource
import com.weatherapp.domain.model.CurrentWeather
import com.weatherapp.domain.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCurrentWeather(latLng: Pair<Double, Double>): Flow<Resource<CurrentWeather>>

    fun getForecastWeather(latLng: Pair<Double, Double>): Flow<Resource<List<WeatherForecast>>>

}