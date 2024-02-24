package com.raq.motori.android.customerapp.base

/**
 * Created by Manoj Sain on 11/01/24.
 */
sealed class ApiState<out T> {
    data object Loading : ApiState<Nothing>()
    data class Success<T>(val data: T) : ApiState<T>()
    data class Error(
        val errorMessage: String,
        val statusCode: Int = ErrorType.UNKNOWN_ERROR.value
    ) : ApiState<Nothing>()
}