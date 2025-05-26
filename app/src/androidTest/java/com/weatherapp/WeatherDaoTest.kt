package com.weatherapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.weatherapp.data.local.AppDatabase
import com.weatherapp.data.local.dao.WeatherDao
import com.weatherapp.data.local.entity.WeatherConditionsEntity
import com.weatherapp.data.local.entity.WeatherForecastEntity
import com.weatherapp.domain.model.WeatherConditionsType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class WeatherDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var weatherDao: WeatherDao
    private val sunriseItem = WeatherConditionsEntity(
        WeatherConditionsType.SUNRISE,
        "08:15",
        "19:57"
    )
    private val windItem = WeatherConditionsEntity(
        WeatherConditionsType.WIND,
        "1234",
        ""
    )
    private val weatherForecastItem = WeatherForecastEntity(
        1234567L,
        "08:47",
        "",
        "18"
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        weatherDao = db.weatherDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertReadWeatherConditionsSuccessfully() = runTest {
        weatherDao.saveWeatherConditions(listOf(sunriseItem))
        assertEquals(1, weatherDao.getWeatherConditions().size)
        weatherDao.saveWeatherConditions(listOf(sunriseItem))
        assertEquals(1, weatherDao.getWeatherConditions().size)
        weatherDao.saveWeatherConditions(listOf(windItem))
        assertEquals(2, weatherDao.getWeatherConditions().size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertReadWeatherForecastSuccessfully() = runTest {
        weatherDao.saveWeatherForecast(listOf(weatherForecastItem))
        assertEquals(1, weatherDao.getWeatherForecast().size)
        weatherDao.clearWeatherForecast()
        assertEquals(0, weatherDao.getWeatherForecast().size)
    }

}