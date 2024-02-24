package com.raq.motori.android.customerapp.base

/**
 * Created by Manoj Sain on 14/01/24.
 */
enum class ErrorType(val value:Int) {
    UN_AUTHORIZED(401),
    SERVER_ERROR(500),
    UNKNOWN_ERROR(520)
}