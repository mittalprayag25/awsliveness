package com.raq.motori.android.customerapp.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * Created by Manoj Sain on 14/01/24.
 */

fun <T> toResultFlow(call: suspend () -> Response<T>): Flow<ApiState<T>> = flow {

    emit(ApiState.Loading)

    try {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiState.Success(it))
            }
        } else {
            response.errorBody()?.let { error ->
                error.close()
                emit(ApiState.Error(error.string(), response.code()))
            }
        }

    } catch (e: Exception) {

        // Uncommenting this to pass Failure to the API calls throughout the app. Otherwise, the state does not receive Fail Callback
        e.printStackTrace()
        emit(ApiState.Error(e.message ?: "Could not find the message"))
    }

}.catch {
    // Pass failure state when the API call faced an exception in any case
    emit(ApiState.Error(it.message ?: "Could not find the message"))
}.flowOn(Dispatchers.IO)