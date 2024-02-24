package com.raq.motori.android.customerapp.di.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class NonAuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        return chain.proceed(requestBuilder.build())
    }
}