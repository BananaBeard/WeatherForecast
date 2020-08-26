package com.kovalenko.weatherforecast.persistence.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kovalenko.weatherforecast.features.forecast.model.domain.City

@Entity
data class DatabaseCity(
    @PrimaryKey
    val name: String,
    val lat: Double,
    val lon: Double
)

fun DatabaseCity.asDomainModel() = City(
    name = this.name,
    lat = this.lat,
    lon = this.lon
)

fun List<DatabaseCity>.asDomainModel() = map { it.asDomainModel() }
