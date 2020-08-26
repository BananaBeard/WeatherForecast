package com.kovalenko.weatherforecast.persistence.database

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application) =
        Room.databaseBuilder(
            application,
            ForecastDatabase::class.java,
            "forecast"
        )
            .fallbackToDestructiveMigration()
            .build()

    fun provideDao(database: ForecastDatabase) = database.forecastDao
    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}
