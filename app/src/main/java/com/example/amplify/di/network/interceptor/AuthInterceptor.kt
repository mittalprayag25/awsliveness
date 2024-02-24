package com.raq.motori.android.customerapp.di.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        val tokenValue = "tokenValue"  //Todo: get token from the saved place and put here
        if (tokenValue.isNotEmpty())
            requestBuilder.addHeader("Authorization", tokenValue)

        return chain.proceed(requestBuilder.build())
    }
}