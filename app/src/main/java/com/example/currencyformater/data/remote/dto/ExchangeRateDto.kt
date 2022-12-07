package com.example.currencyformater.data.remote.dto

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ExchangeRateDto(
    @SerializedName("base")
    val base: String? = "",
    @SerializedName("date")
    val date: String? = "",
    @SerializedName("rates")
    val rates: JsonObject,
    @SerializedName("success")
    val success: Boolean? = true,
    @SerializedName("timestamp")
    val timestamp: Int? = 0
)