package com.kovalenko.weatherforecast.persistence.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kovalenko.weatherforecast.features.forecast.model.database.DatabaseForecast
import com.kovalenko.weatherforecast.persistence.database.entities.DatabaseCity
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: DatabaseCity)

    @Query("SELECT * FROM databasecity ORDER BY name ASC")
    fun getCities(): Flow<List<DatabaseCity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: DatabaseForecast)

    @Query("SELECT * FROM databaseforecast WHERE cityName=:city")
    fun getForecast(city: String): Flow<DatabaseForecast?>
}
