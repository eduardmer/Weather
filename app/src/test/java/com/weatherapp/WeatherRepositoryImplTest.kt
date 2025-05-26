package com.weatherapp

import com.weatherapp.common.Resource
import com.weatherapp.data.repository.WeatherRepositoryImpl
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private lateinit var api: FakeWeatherApi
    private lateinit var dao: FakeWeatherDao
    private lateinit var repository: WeatherRepositoryImpl
    private val latLng = Pair(12.0, 77.0)

    @Before
    fun setup() {
        api = FakeWeatherApi()
        dao = FakeWeatherDao()
        repository = WeatherRepositoryImpl(api, dao)
    }

    @Test
    fun `getCurrentWeather returns success`() = runTest {
        val result = repository.getCurrentWeather(latLng).toList()
        assert(result[0] is Resource.Loading && result[1] is Resource.Success<*>)
    }

    @Test
    fun `getCurrentWeather returns error on exception`() = runTest {
        api.currentWeatherShouldThrow = true
        val result = repository.getCurrentWeather(latLng).toList()
        assert(result[0] is Resource.Loading && result[1] is Resource.Error<*>)
    }

    @Test
    fun `getForecastWeather returns success`() = runTest {
        val result = repository.getForecastWeather(latLng).toList()
        assert(result[0] is Resource.Loading && result[1] is Resource.Success<*>)
    }

    @Test
    fun `getForecastWeather returns error on exception`() = runTest {
        api.forecastWeatherShouldThrow = true
        val result = repository.getForecastWeather(latLng).toList()
        assert(result[0] is Resource.Loading && result[1] is Resource.Error<*>)
    }

}
