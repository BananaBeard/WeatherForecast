package com.kovalenko.weatherforecast.features.forecast.viewmodel

import androidx.lifecycle.*
import com.kovalenko.weatherforecast.features.forecast.model.domain.City
import com.kovalenko.weatherforecast.features.forecast.model.repository.ForecastRepository

class ForecastViewModel(private val forecastRepository: ForecastRepository) : ViewModel() {
    private val _city = MutableLiveData<City>()
    val city: LiveData<City>
        get() = _city

    val forecast = Transformations.switchMap(city) { city ->
        forecastRepository.getWeather(city).asLiveData(viewModelScope.coroutineContext)
    }

    fun loadWeather(city: City) {
        _city.value = city
    }
}
