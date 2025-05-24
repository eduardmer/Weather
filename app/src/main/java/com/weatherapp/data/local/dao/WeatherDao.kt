package com.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weatherapp.data.local.entity.WeatherConditionsEntity
import com.weatherapp.data.local.entity.WeatherForecastEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherConditionsEntity")
    suspend fun getWeatherConditions(): List<WeatherConditionsEntity>

    @Query("SELECT * FROM WeatherForecastEntity")
    suspend fun getWeatherForecast(): List<WeatherForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherConditions(items: List<WeatherConditionsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherForecast(items: List<WeatherForecastEntity>)

    @Query("DELETE FROM WeatherForecastEntity")
    suspend fun clearWeatherForecast()

}