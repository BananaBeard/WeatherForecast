package com.kovalenko.weatherforecast.features.forecast.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kovalenko.weatherforecast.features.forecast.model.domain.DailyForecast
import com.kovalenko.weatherforecast.features.forecast.model.domain.Forecast

@Entity
@TypeConverters(DailyForecastTypeConverter::class)
data class DatabaseForecast(
        @PrimaryKey
        var cityName: String = "",
        val timeZoneOffset: Long,
        val temp: Double,
        val feelsLike: Double,
        val maxTemp: Double,
        val minTemp: Double,
        val humidity: Int,
        val description: String,
        val daily: List<DatabaseDailyForecast>
)

data class DatabaseDailyForecast(
        val time: Long,
        val temp: Double,
        val maxTemp: Double,
        val minTemp: Double,
        val windSpeed: Double,
        val humidity: Int,
        val description: String,
        val icon: String
)

fun DatabaseForecast.asDomainModel() = Forecast(
        cityName = cityName,
        timeZoneOffset = timeZoneOffset,
        temp = temp,
        feelsLike = feelsLike,
        maxTemp = maxTemp,
        minTemp = minTemp,
        humidity = humidity,
        description = description,
        daily = daily.asDomainModel()
)

fun DatabaseDailyForecast.asDomainModel() = DailyForecast(
        time = time,
        temp = temp,
        maxTemp = maxTemp,
        minTemp = minTemp,
        windSpeed = windSpeed,
        humidity = humidity,
        description = description,
        icon = icon
)

fun List<DatabaseDailyForecast>.asDomainModel() = map { it.asDomainModel() }

class DailyForecastTypeConverter {
    @TypeConverter
    fun fromDailyForecastList(list: List<DatabaseDailyForecast>?): String? {
        return list?.let {
            val gson = Gson()
            val type = object : TypeToken<List<DatabaseDailyForecast>>() {}.type
            gson.toJson(list, type).toString()
        }
    }

    @TypeConverter
    fun toIngredientList(json: String?): List<DatabaseDailyForecast> {
        return json?.let {
            val gson = Gson()
            val type = object : TypeToken<List<DatabaseDailyForecast>>() {}.type
            gson.fromJson<List<DatabaseDailyForecast>>(it, type)
        } ?: emptyList()
    }
}
