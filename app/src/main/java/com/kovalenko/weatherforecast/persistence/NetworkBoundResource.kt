package com.kovalenko.weatherforecast.persistence

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.kovalenko.weatherforecast.network.ApiErrorResponse
import com.kovalenko.weatherforecast.network.ApiResponse
import com.kovalenko.weatherforecast.network.ApiSuccessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {
    fun asFlow() = flow {
        val dbValue = loadFromDb().first()
        if (shouldFetch(dbValue)) {
            emit(Resource.loading(dbValue))
            fetchFromNetwork().collect { response ->
                when (response) {
                    is ApiSuccessResponse -> {
                        saveNetworkResult(processResponse(response))
                        emitAll(loadFromDb().map { Resource.success(it) })
                    }
                    is ApiErrorResponse -> {
                        emitAll(loadFromDb().map { Resource.error(response.errorMessage, it) })
                    }
                }
            }
        } else {
            emitAll(loadFromDb().map { Resource.success(it) })
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @MainThread
    protected abstract suspend fun saveNetworkResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Flow<ResultType>

    @MainThread
    protected abstract fun fetchFromNetwork(): Flow<ApiResponse<RequestType>>
}
