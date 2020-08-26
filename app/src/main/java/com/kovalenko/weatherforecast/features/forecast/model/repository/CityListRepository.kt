package com.kovalenko.weatherforecast.features.forecast.model.repository

import com.kovalenko.weatherforecast.persistence.database.ForecastDao
import com.kovalenko.weatherforecast.persistence.database.entities.asDomainModel
import kotlinx.coroutines.flow.map

class CityListRepository(private val forecastDao: ForecastDao) {
    fun getCities() = forecastDao.getCities().map { it.asDomainModel() }
}
