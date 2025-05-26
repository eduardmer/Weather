package com.weatherapp

import com.weatherapp.data.remote.dto.CurrentWeatherDto
import com.weatherapp.data.remote.dto.Main
import com.weatherapp.data.remote.dto.Sys
import com.weatherapp.data.remote.dto.Weather
import com.weatherapp.data.remote.dto.WeatherForecastDto
import com.weatherapp.data.remote.dto.WeatherForecastItem
import com.weatherapp.data.remote.dto.Wind

object FakeData {
    val currentWeatherDto = CurrentWeatherDto(
        200,
        Main(
            12.0,
            1,
            1,
            1,
            1,
            1.0,
            1.0,
            1.0,
        ),
        "New York",
        Sys(
            "New York",
            21424,
            231423
        ),
        listOf(
            Weather(
                "",
                "",
                1,
                ""
            )
        ),
        Wind(
            1,
            1.0,
            1.0
        )
    )
    val forecastDto = WeatherForecastDto(
        200,
        list = listOf(
            WeatherForecastItem(
                1234567L,
                Main(
                    12.0,
                    1,
                    1,
                    1,
                    1,
                    1.0,
                    1.0,
                    1.0,
                ),
                listOf(
                    Weather(
                        "",
                        "",
                        1,
                        ""
                    )
                )
            )
        )
    )
}