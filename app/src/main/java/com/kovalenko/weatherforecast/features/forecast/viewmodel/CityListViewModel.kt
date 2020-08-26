package com.kovalenko.weatherforecast.features.forecast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kovalenko.weatherforecast.features.forecast.model.repository.CityListRepository

class CityListViewModel(private val cityListRepository: CityListRepository) : ViewModel() {
    val cities = cityListRepository.getCities().asLiveData(viewModelScope.coroutineContext)
}
