package com.weatherapp.ui

sealed class Screen(val route: String) {
    object WeatherScreen: Screen("weather_screen")
}