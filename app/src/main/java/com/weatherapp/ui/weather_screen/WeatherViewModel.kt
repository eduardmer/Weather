package com.weatherapp.ui.weather_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.common.Resource
import com.weatherapp.domain.use_case.GetCurrentWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel() {

    val _state = mutableStateOf(WeatherState())
    //val state: State<WeatherState> = _state

    init {
        getCurrentWeatherUseCase().map {
            when(it) {
                is Resource.Success -> {
                    _state.value = WeatherState(
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

    fun dismissError() {
        _state.value = _state.value.copy(error = null)
    }

}