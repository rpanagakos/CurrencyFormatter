package com.example.currencyformater.data.repository

import com.example.currencyformater.data.remote.CurrencyApi
import com.example.currencyformater.data.remote.dto.ExchangeRateDto
import com.example.currencyformater.domain.respository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api : CurrencyApi
) : CurrencyRepository{

    override suspend fun getLatestRates(): ExchangeRateDto {
        return api.getLatestRates()
    }
}