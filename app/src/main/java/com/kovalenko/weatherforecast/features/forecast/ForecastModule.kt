package com.kovalenko.weatherforecast.features.forecast

import com.kovalenko.weatherforecast.features.forecast.model.network.ForecastService
import com.kovalenko.weatherforecast.features.forecast.model.repository.CityListRepository
import com.kovalenko.weatherforecast.features.forecast.model.repository.ForecastRepository
import com.kovalenko.weatherforecast.features.forecast.viewmodel.ForecastViewModel
import com.kovalenko.weatherforecast.features.forecast.viewmodel.CityListViewModel
import com.kovalenko.weatherforecast.persistence.database.ForecastDao
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val forecastModule = module {
    fun provideListRepository(dao: ForecastDao) = CityListRepository(dao)
    fun provideForecastRepository(api: ForecastService, dao: ForecastDao) =
        ForecastRepository(api, dao)

    fun provideForecastApi(retrofit: Retrofit) = retrofit.create(ForecastService::class.java)

    single { provideForecastApi(get()) }
    single { provideListRepository(get()) }
    single { provideForecastRepository(get(), get()) }
    viewModel { CityListViewModel(get()) }
    viewModel { ForecastViewModel(get()) }
}
