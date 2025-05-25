package com.weatherapp.domain.use_case

import com.weatherapp.common.Resource
import com.weatherapp.domain.model.Weather
import com.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(latLng: Pair<Double, Double>): Flow<Resource<Weather>> =
        repository.getCurrentWeather(latLng).combine(repository.getForecastWeather(latLng)) { dailyWeather, forecastWeather ->
            when {
                dailyWeather is Resource.Loading || forecastWeather is Resource.Loading -> Resource.Loading<Weather>()
                dailyWeather is Resource.Error || forecastWeather is Resource.Error ->
                    Resource.Error(
                        dailyWeather.message.orEmpty().ifEmpty {
                            forecastWeather.message.orEmpty()
                        },
                        Weather(
                            dailyWeather.data?.isDay != false,
                            dailyWeather.data?.cityDetails,
                            dailyWeather.data?.details.orEmpty(),
                            forecastWeather.data.orEmpty()
                        )
                    )
                else -> Resource.Success(
                    Weather(
                        dailyWeather.data?.isDay != false,
                        dailyWeather.data?.cityDetails,
                        dailyWeather.data?.details.orEmpty(),
                        forecastWeather.data.orEmpty()
                    )
                )
            }
        }

}