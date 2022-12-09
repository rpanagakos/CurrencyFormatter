package com.example.currencyformater.data.remote

import com.example.currencyformater.data.remote.dto.ExchangeRateDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/exchangerates_data/latest")
    suspend fun getLatestRates(
        @Query("base") baseCurrency: String
    ): ExchangeRateDto
}