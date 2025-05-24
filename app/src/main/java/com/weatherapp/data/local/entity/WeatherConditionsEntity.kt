package com.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weatherapp.domain.model.WeatherConditionsType
import com.weatherapp.domain.model.WeatherConditions

@Entity
data class WeatherConditionsEntity(
    @PrimaryKey
    val detailType: WeatherConditionsType,
    val description: String,
    val additionalDetails: String
)

fun WeatherConditionsEntity.toDomainModel() = WeatherConditions(
    detailType,
    description,
    additionalDetails
)
