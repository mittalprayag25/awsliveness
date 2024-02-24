package com.raq.motori.android.customerapp.di

import com.example.amplify.data.repository.ApiRepositoryImpl
import com.raq.motori.android.customerapp.home.domain.repository.ApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(impl: ApiRepositoryImpl): ApiRepository

}