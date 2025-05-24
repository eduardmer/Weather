package com.weatherapp.data.remote

import com.weatherapp.BuildConfig
import com.weatherapp.data.remote.dto.CurrentWeatherDto
import com.weatherapp.data.remote.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lng: Double,
        @Query("appid") appid: String = BuildConfig.APP_ID
    ): CurrentWeatherDto

    @GET("forecast")
    suspend fun getForecastWeather(
        @Query("lat") lat : Double,
        @Query("lon") lng : Double,
        @Query("appid") appid : String = BuildConfig.APP_ID
    ) : WeatherForecastDto

}