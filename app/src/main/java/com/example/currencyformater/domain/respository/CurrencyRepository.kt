package com.example.currencyformater.domain.respository

import com.example.currencyformater.data.remote.dto.ExchangeRateDto
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface CurrencyRepository {

    suspend fun getLatestRates(baseCurrency : String) : ExchangeRateDto
}