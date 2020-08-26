package com.kovalenko.weatherforecast.network

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

@ExperimentalCoroutinesApi
class FlowCallAdapter<R>(private val responseType: Type) : CallAdapter<R, Flow<ApiResponse<R>>> {
    override fun adapt(call: Call<R>): Flow<ApiResponse<R>> {
        return callbackFlow {
            call.enqueue(object : Callback<R> {
                override fun onFailure(call: Call<R>, throwable: Throwable) {
                    offer(ApiResponse.create<R>(throwable))

                }

                override fun onResponse(call: Call<R>, response: Response<R>) {
                    offer(ApiResponse.create(response))
                }
            })
            awaitClose { call.cancel() }
        }
    }

    override fun responseType() = responseType
}
