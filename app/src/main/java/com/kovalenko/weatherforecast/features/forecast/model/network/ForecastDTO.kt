package com.kovalenko.weatherforecast.features.forecast.model.network

import com.google.gson.annotations.SerializedName
import com.kovalenko.weatherforecast.features.forecast.model.database.DatabaseDailyForecast
import com.kovalenko.weatherforecast.features.forecast.model.database.DatabaseForecast

data class ForecastResponse(
        @SerializedName("timezone_offset")
        val timeZoneOffset: Long,
        @SerializedName("current")
        val currentWeather: CurrentWeatherDTO,
        val daily: List<DailyWeatherDTO>
)

data class CurrentWeatherDTO(
        @SerializedName("dt")
        val time: Long,
        val temp: Double,
        @SerializedName("feels_like")
        val feelsLike: Double,
        val humidity: Int,
        @SerializedName("wind_speed")
        val windSpeed: Double,
        val weather: List<WeatherDTO>
)

data class WeatherDTO(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
)

data class DailyWeatherDTO(
        @SerializedName("dt")
        val time: Long,
        val temp: TemperatureDTO,
        val humidity: Int,
        @SerializedName("wind_speed")
        val windSpeed: Double,
        val weather: List<WeatherDTO>
)

data class TemperatureDTO(
        val day: Double,
        val min: Double,
        val max: Double
)

fun ForecastResponse.asDatabaseModel(): DatabaseForecast {
    return DatabaseForecast(
            timeZoneOffset = timeZoneOffset,
            temp = currentWeather.temp,
            feelsLike = currentWeather.feelsLike,
            maxTemp = daily[0].temp.max,
            minTemp = daily[0].temp.min,
            humidity = currentWeather.humidity,
            description = currentWeather.weather[0].main,
            daily = daily.asDatabaseModel()
    )
}

fun DailyWeatherDTO.asDatabaseModel(): DatabaseDailyForecast {
    return DatabaseDailyForecast(
            time = time,
            temp = temp.day,
            maxTemp = temp.max,
            minTemp = temp.min,
            windSpeed = windSpeed,
            humidity = humidity,
            description = weather[0].main,
            icon = weather[0].icon
    )
}

fun List<DailyWeatherDTO>.asDatabaseModel() = map { it.asDatabaseModel() }


