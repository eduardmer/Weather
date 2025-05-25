package com.weatherapp.ui.weather_screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.weatherapp.R
import com.weatherapp.domain.model.CityDetails
import com.weatherapp.domain.model.WeatherConditions
import com.weatherapp.domain.model.WeatherConditionsType.*
import com.weatherapp.domain.model.WeatherForecast

data class WeatherState(
    @DrawableRes
    val backgroundImage: Int = R.drawable.bg_day,
    val city: CityDetails? = null,
    val weatherConditions: List<WeatherConditionsUi> = emptyList(),
    val weatherForecast: List<WeatherForecast> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)

data class WeatherConditionsUi(
    @StringRes
    val titleId: Int,
    @DrawableRes
    val iconId: Int,
    val description: String,
    val additionalDatas: String
)

fun WeatherConditions.toUiModel() = WeatherConditionsUi(
    titleId = when (type) {
        SUNRISE -> R.string.sunrise
        WIND -> R.string.wind
        HUMIDITY -> R.string.humidity
        RAINFALL -> R.string.rainfall
        FEELS_LIKE -> R.string.feels_like
        PRESSURE -> R.string.pressure
    },
    iconId = when (type) {
        SUNRISE -> R.drawable.ic_sunrise
        WIND -> R.drawable.ic_wind
        HUMIDITY -> R.drawable.ic_humidity
        RAINFALL -> R.drawable.ic_rain
        FEELS_LIKE -> R.drawable.ic_thermometer
        PRESSURE -> R.drawable.ic_pressure
    },
    description = description,
    additionalDatas = additionalDatas
)
