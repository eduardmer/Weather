package com.weatherapp.data.local

import androidx.room.TypeConverter
import com.weatherapp.domain.model.WeatherConditionsType

class TypeConverters {

    @TypeConverter
    fun enumToInt(value: WeatherConditionsType): Int = value.ordinal

    @TypeConverter
    fun intToEnum(nr: Int): WeatherConditionsType = enumValues<WeatherConditionsType>()[nr]

}