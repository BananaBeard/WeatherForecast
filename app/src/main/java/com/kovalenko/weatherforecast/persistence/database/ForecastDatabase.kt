package com.kovalenko.weatherforecast.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kovalenko.weatherforecast.features.forecast.model.database.DatabaseForecast
import com.kovalenko.weatherforecast.persistence.database.entities.DatabaseCity

@Database(entities = [DatabaseCity::class, DatabaseForecast::class], version = 2)
abstract class ForecastDatabase : RoomDatabase() {
    abstract val forecastDao: ForecastDao
}
