package com.kovalenko.weatherforecast.features.forecast.model.network

import com.kovalenko.weatherforecast.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {
    @GET("onecall")
    fun getForecast(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("exclude") exclude: String,
            @Query("units") units: String,
            @Query("appId") appId: String,
    ): Flow<ApiResponse<ForecastResponse>>
}
