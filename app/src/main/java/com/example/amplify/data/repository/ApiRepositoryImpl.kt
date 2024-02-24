package com.example.amplify.data.repository

import com.raq.motori.android.customerapp.base.ApiState
import com.raq.motori.android.customerapp.base.toResultFlow
import com.raq.motori.android.customerapp.di.network.ApiService
import com.raq.motori.android.customerapp.di.network.qualifier.NonAuthApiService
import com.raq.motori.android.customerapp.home.domain.model.Product
import com.raq.motori.android.customerapp.home.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Manoj Sain on 14/01/24.
 */
class ApiRepositoryImpl @Inject constructor(
    @NonAuthApiService private val apiService: ApiService
): ApiRepository {
    override suspend fun getProducts(): Flow<ApiState<List<Product>>> {
        return toResultFlow { apiService.getProduct() }
    }

    /**
     *  Todo: change your Request & Response format here
     */
    override suspend fun postData(product: Product): Flow<ApiState<List<Product>>> {
        return toResultFlow { apiService.postData(product) }
    }
}