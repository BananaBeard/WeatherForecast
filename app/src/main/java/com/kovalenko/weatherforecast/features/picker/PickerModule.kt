package com.kovalenko.weatherforecast.features.picker

import com.kovalenko.weatherforecast.features.picker.model.repository.PickerRepository
import com.kovalenko.weatherforecast.features.picker.viewmodel.PlacePickerViewModel
import com.kovalenko.weatherforecast.persistence.database.ForecastDao
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pickerModule = module {
    fun provideRepository(dao: ForecastDao) = PickerRepository(dao)

    single { provideRepository(get()) }
    viewModel { PlacePickerViewModel(get()) }
}
