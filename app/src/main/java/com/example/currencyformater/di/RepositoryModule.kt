package com.example.currencyformater.di

import com.example.currencyformater.data.repository.CurrencyRepositoryImpl
import com.example.currencyformater.domain.respository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideCurrencyRepository(currencyRepositoryImpl: CurrencyRepositoryImpl) : CurrencyRepository

}