package com.weatherapp

import com.weatherapp.common.Resource
import com.weatherapp.data.repository.WeatherRepositoryImpl
import com.weatherapp.domain.model.Weather
import com.weatherapp.domain.use_case.GetCurrentWeatherUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetCurrentWeatherUseCaseTest {

    private lateinit var api: FakeWeatherApi
    private lateinit var dao: FakeWeatherDao
    private lateinit var repository: WeatherRepositoryImpl
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    private val latLng = Pair(12.0, 77.0)

    @Before
    fun setup() {
        api = FakeWeatherApi()
        dao = FakeWeatherDao()
        repository = WeatherRepositoryImpl(api, dao)
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(repository)
    }

    @Test
    fun `returns success when both flows return success`() = runTest {
        api.currentWeatherShouldThrow = false
        api.forecastWeatherShouldThrow = false

        val emissions = mutableListOf<Resource<Weather>>()
        getCurrentWeatherUseCase(latLng).collect {
            emissions.add(it)
        }

        val result = (emissions.last() as? Resource.Success)?.data
        assertEquals("New York", result?.cityDetails?.name)
    }

    @Test
    fun `returns loading when one flow emits loading`() = runTest {
        val emissions = mutableListOf<Resource<Weather>>()
        getCurrentWeatherUseCase(latLng).collect {
            emissions.add(it)
        }
        assertTrue(emissions.first() is Resource.Loading)
    }

    @Test
    fun `returns error when one flow emits error`() = runTest {
        api.currentWeatherShouldThrow = true
        val emissions = mutableListOf<Resource<Weather>>()
        getCurrentWeatherUseCase(latLng).collect {
            emissions.add(it)
        }
        assertTrue(emissions.last() is Resource.Error)
    }

}