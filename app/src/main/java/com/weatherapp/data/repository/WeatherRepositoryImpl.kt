package com.weatherapp.data.repository

import com.weatherapp.common.Resource
import com.weatherapp.data.local.dao.WeatherDao
import com.weatherapp.data.local.entity.toDomainModel
import com.weatherapp.data.remote.WeatherApi
import com.weatherapp.data.remote.dto.toDomainModel
import com.weatherapp.data.remote.dto.toEntityModel
import com.weatherapp.domain.model.WeatherDaily
import com.weatherapp.domain.model.WeatherForecast
import com.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun getCurrentWeather(): Flow<Resource<WeatherDaily>> = flow {
        try {
            emit(Resource.Loading<WeatherDaily>())
            val response = api.getCurrentWeather(41.234, 19.12)
            weatherDao.saveWeatherConditions(response.toEntityModel())
            emit(Resource.Success(response.toDomainModel()))
        } catch(e: IOException) {
            emit(Resource.Error<WeatherDaily>(
                "Couldn't reach server. Check your internet connection.",
                WeatherDaily(
                    null,
                    weatherDao.getWeatherConditions().map { it.toDomainModel() }
                )
            ))
        } catch (e: Exception) {
            emit(Resource.Error<WeatherDaily>(
                e.localizedMessage ?: "An unexpected error occured",
                WeatherDaily(
                    null,
                    weatherDao.getWeatherConditions().map { it.toDomainModel() }
                )
            ))
        }
    }

    override fun getForecastWeather(): Flow<Resource<List<WeatherForecast>>> = flow {
        try {
            emit(Resource.Loading<List<WeatherForecast>>())
            val response = api.getForecastWeather(41.234, 19.12).list
            weatherDao.clearWeatherForecast()
            weatherDao.saveWeatherForecast(response.map { it.toEntityModel() })
            emit(Resource.Success(response.map { it.toDomainModel() }))
        } catch(e: IOException) {
            emit(Resource.Error<List<WeatherForecast>>(
                "Couldn't reach server. Check your internet connection.",
                weatherDao.getWeatherForecast().map { it.toDomainModel() }
            ))
        } catch (e: Exception) {
            emit(Resource.Error<List<WeatherForecast>>(
                e.localizedMessage ?: "An unexpected error occured",
                weatherDao.getWeatherForecast().map { it.toDomainModel() }
            ))
        }
    }

}