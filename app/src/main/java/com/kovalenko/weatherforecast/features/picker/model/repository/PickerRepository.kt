package com.kovalenko.weatherforecast.features.picker.model.repository

import com.google.android.gms.maps.model.LatLng
import com.kovalenko.weatherforecast.persistence.database.entities.DatabaseCity
import com.kovalenko.weatherforecast.persistence.database.ForecastDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PickerRepository(private val forecastDao: ForecastDao) {
    suspend fun saveCity(name: String, latLng: LatLng) = withContext(Dispatchers.IO){
        forecastDao.insertCity(DatabaseCity(name, latLng.latitude, latLng.longitude))
    }
}
