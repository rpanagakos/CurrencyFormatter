package com.example.currencyformater.domain.model

import com.example.currencyformater.data.remote.dto.ExchangeRateDto
import com.google.gson.Gson
import com.google.gson.JsonObject

data class ExchangeRateData(
    val base: String = "",
    val date: String = "",
    val rates: List<CurrencyRate> = emptyList(),
    val success: Boolean = true,
    val timestamp: Int = 0
) {
    constructor(dto: ExchangeRateDto) : this(
        base = dto.base ?: "",
        date = dto.date ?: "",
        rates = convertJsonObjectRatesToData(dto.rates)
    )
}

private fun convertJsonObjectRatesToData(jsonObject: JsonObject): List<CurrencyRate> {
    return try {
        val gson = Gson()
        val mutableListRate: MutableList<CurrencyRate> = mutableListOf()
        val data = jsonObject.toString()
        val map = gson.fromJson<Map<String, Double>>(data, MutableMap::class.java)
        map.forEach { (id, rate) ->
            val currencyRate = CurrencyRate(id, rate.toDouble())
            mutableListRate.add(currencyRate)
        }
        mutableListRate
    } catch (e: java.lang.Exception) {
        emptyList()
    }
}
