package com.kovalenko.weatherforecast

import android.app.Application
import com.kovalenko.weatherforecast.features.forecast.forecastModule
import com.kovalenko.weatherforecast.features.picker.pickerModule
import com.kovalenko.weatherforecast.network.networkModule
import com.kovalenko.weatherforecast.persistence.database.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherForecastApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherForecastApplication)
            modules(listOf(databaseModule, pickerModule, forecastModule, networkModule))
        }
    }
}
