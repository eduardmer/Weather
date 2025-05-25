package com.weatherapp.ui.weather_screen

sealed interface EventType {

    object DismissDialogEvent : EventType
    data class GetWeatherEvent(val latLng: Pair<Double, Double>) : EventType

}