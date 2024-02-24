package com.raq.motori.android.customerapp.home.domain.repository

import com.raq.motori.android.customerapp.base.ApiState
import com.raq.motori.android.customerapp.home.domain.model.Product
import kotlinx.coroutines.flow.Flow

/**
 * Created by Manoj Sain on 14/01/24.
 */
interface ApiRepository {
    suspend fun getProducts(): Flow<ApiState<List<Product>>>

    suspend fun postData(product: Product): Flow<ApiState<List<Product>>> // Change your Request & Response format
}