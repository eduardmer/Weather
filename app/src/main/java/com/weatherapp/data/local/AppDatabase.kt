package com.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.weatherapp.data.local.dao.WeatherDao
import com.weatherapp.data.local.entity.WeatherConditionsEntity
import com.weatherapp.data.local.entity.WeatherForecastEntity

@Database(entities = [WeatherConditionsEntity::class, WeatherForecastEntity::class], version = AppDatabase.DB_VERSION)
@TypeConverters(com.weatherapp.data.local.TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "med_database"
        const val DB_VERSION = 1
    }

    abstract fun weatherDao(): WeatherDao

}