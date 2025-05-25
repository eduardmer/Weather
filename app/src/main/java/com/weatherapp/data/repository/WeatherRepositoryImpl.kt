package com.weatherapp.data.repository

import com.weatherapp.common.Resource
import com.weatherapp.data.local.dao.WeatherDao
import com.weatherapp.data.local.entity.toDomainModel
import com.weatherapp.data.remote.WeatherApi
import com.weatherapp.data.remote.dto.toDomainModel
import com.weatherapp.data.remote.dto.toEntityModel
import com.weatherapp.domain.model.CurrentWeather
import com.weatherapp.domain.model.WeatherForecast
import com.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun getCurrentWeather(latLng: Pair<Double, Double>): Flow<Resource<CurrentWeather>> = flow {
        try {
            emit(Resource.Loading<CurrentWeather>())
            val response = api.getCurrentWeather(latLng.first, latLng.second)
            weatherDao.saveWeatherConditions(response.toEntityModel())
            emit(Resource.Success(response.toDomainModel()))
        } catch(e: IOException) {
            emit(Resource.Error<CurrentWeather>(
                "Couldn't reach server. Check your internet connection.",
                CurrentWeather(
                    cityDetails = null,
                    details = weatherDao.getWeatherConditions().map { it.toDomainModel() }
                )
            ))
        } catch (e: Exception) {
            emit(Resource.Error<CurrentWeather>(
                e.localizedMessage ?: "An unexpected error occured",
                CurrentWeather(
                    cityDetails = null,
                    details = weatherDao.getWeatherConditions().map { it.toDomainModel() }
                )
            ))
        }
    }.flowOn(Dispatchers.IO)

    override fun getForecastWeather(latLng: Pair<Double, Double>): Flow<Resource<List<WeatherForecast>>> = flow {
        try {
            emit(Resource.Loading<List<WeatherForecast>>())
            val response = api.getForecastWeather(latLng.first, latLng.second).list
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
    }.flowOn(Dispatchers.IO)

}