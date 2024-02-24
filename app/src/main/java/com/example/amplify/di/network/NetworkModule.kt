package com.raq.motori.android.customerapp.di.network

import com.raq.motori.android.customerapp.di.network.interceptor.AuthInterceptor
import com.raq.motori.android.customerapp.di.network.interceptor.NonAuthInterceptor
import com.raq.motori.android.customerapp.di.network.qualifier.AuthInterceptorClient
import com.raq.motori.android.customerapp.di.network.qualifier.NonAuthApiService
import com.raq.motori.android.customerapp.di.network.qualifier.NonAuthInterceptorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        // Prepare OkHTTP Client to use interceptor for Network Requests
        val builder = OkHttpClient.Builder()
            .callTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-source", "Android")
                    .addHeader("x-language", Locale.getDefault().language)
                    .build()
                chain.proceed(request)
            })
        return builder.build()
    }

    @Provides
    @Singleton
    @NonAuthInterceptorClient
    fun provideNonAuthOkHttpClient(okHttpClient: OkHttpClient): OkHttpClient {
        return okHttpClient.newBuilder().addInterceptor(NonAuthInterceptor()).build()
    }

    @Provides
    @Singleton
    @AuthInterceptorClient
    fun provideAuthOkHttpClient(okHttpClient: OkHttpClient): OkHttpClient {
        return okHttpClient.newBuilder().addInterceptor(AuthInterceptor()).build()
    }


    @Provides
    @Singleton
    @NonAuthApiService
    fun provideNonAuthApiServiceRetrofit(@NonAuthInterceptorClient client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")//Todo: change your base url here
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApiRestServiceRetrofit(@AuthInterceptorClient client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("Base URL")//Todo: change your base url here
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}