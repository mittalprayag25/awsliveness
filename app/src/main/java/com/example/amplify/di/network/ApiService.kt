package com.raq.motori.android.customerapp.di.network

import com.raq.motori.android.customerapp.home.domain.model.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/products")
    suspend fun getProduct(): Response<List<Product>>

    /**
     *  Todo: you can use post call with your body as data class
     */
    @POST("/products")
    suspend fun postData(
        @Body product: Product
    ): Response<List<Product>>
}