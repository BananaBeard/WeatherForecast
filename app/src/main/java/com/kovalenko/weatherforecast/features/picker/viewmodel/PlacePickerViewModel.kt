package com.kovalenko.weatherforecast.features.picker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.kovalenko.weatherforecast.features.picker.model.repository.PickerRepository
import kotlinx.coroutines.launch

class PlacePickerViewModel(private val pickerRepository: PickerRepository) : ViewModel() {
    fun saveLocation(name: String, latLng: LatLng) {
        viewModelScope.launch {
            pickerRepository.saveCity(name, latLng)
        }
    }
}
