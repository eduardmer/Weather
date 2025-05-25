package com.weatherapp.ui.weather_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.R
import com.weatherapp.common.Resource
import com.weatherapp.domain.use_case.GetCurrentWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map
import kotlin.collections.orEmpty

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel() {

    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> get() = _state
    private var job: Job? = null

    fun onEvent(event: EventType) {
        when (event) {
            EventType.DismissDialogEvent -> _state.value = _state.value.copy(error = null)
            is EventType.GetWeatherEvent -> getWeather(event.latLng)
        }
    }

    private fun getWeather(latLng: Pair<Double, Double>) {
        job?.cancel()
        job = getCurrentWeatherUseCase(latLng).map {
            when(it) {
                is Resource.Success -> {
                    _state.value = WeatherState(
                        backgroundImage = if (it.data?.isDay == true) R.drawable.bg_day else R.drawable.bg_night,
                        city = it.data?.cityDetails,
                        weatherConditions = it.data?.conditions.orEmpty().map { item ->
                            item.toUiModel()
                        },
                        weatherForecast = it.data?.forecast.orEmpty(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = WeatherState(
                        backgroundImage = if (it.data?.isDay == true) R.drawable.bg_day else R.drawable.bg_night,
                        city = it.data?.cityDetails,
                        weatherConditions = it.data?.conditions.orEmpty().map { item ->
                            item.toUiModel()
                        },
                        weatherForecast = it.data?.forecast.orEmpty(),
                        error = it.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}