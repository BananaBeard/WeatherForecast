package com.kovalenko.weatherforecast.features.forecast.model.domain

data class Forecast(
        val cityName: String,
        val timeZoneOffset: Long,
        val temp: Double,
        val feelsLike: Double,
        val maxTemp: Double,
        val minTemp: Double,
        val humidity: Int,
        val description: String,
        val daily: List<DailyForecast>
)

data class DailyForecast(
        val time: Long,
        val temp: Double,
        val maxTemp: Double,
        val minTemp: Double,
        val windSpeed: Double,
        val humidity: Int,
        val description: String,
        val icon: String
)
