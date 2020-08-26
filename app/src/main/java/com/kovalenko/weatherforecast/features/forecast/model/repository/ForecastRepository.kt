package com.kovalenko.weatherforecast.features.forecast.model.repository

import com.kovalenko.weatherforecast.features.forecast.model.database.asDomainModel
import com.kovalenko.weatherforecast.features.forecast.model.domain.City
import com.kovalenko.weatherforecast.features.forecast.model.domain.Forecast
import com.kovalenko.weatherforecast.features.forecast.model.network.ForecastResponse
import com.kovalenko.weatherforecast.features.forecast.model.network.ForecastService
import com.kovalenko.weatherforecast.features.forecast.model.network.asDatabaseModel
import com.kovalenko.weatherforecast.persistence.NetworkBoundResource
import com.kovalenko.weatherforecast.persistence.database.ForecastDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ForecastRepository(
        private val forecastApi: ForecastService,
        private val forecastDao: ForecastDao
) {
    fun getWeather(city: City) =
            object : NetworkBoundResource<Forecast?, ForecastResponse>() {
                override suspend fun saveNetworkResult(item: ForecastResponse) =
                        forecastDao.insertForecast(item.asDatabaseModel().apply { cityName = city.name })

                override fun shouldFetch(data: Forecast?) = true

                override fun loadFromDb(): Flow<Forecast?> =
                        forecastDao.getForecast(city.name).map { it?.asDomainModel() }

                override fun fetchFromNetwork() = forecastApi.getForecast(
                        lat = city.lat,
                        lon = city.lon,
                        exclude = "hourly,minutely",
                        units = "metric",
                        appId = "524d6c6fa08cd440b6fb0c8e4bca5510"
                )
            }.asFlow()
}
