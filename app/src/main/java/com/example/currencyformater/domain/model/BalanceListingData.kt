package com.example.currencyformater.domain.model

import androidx.compose.runtime.Stable
import com.example.currencyformater.data.local.BalanceListingEntity

@Stable
data class BalanceListingData(
    val name: String = "",
    val balance: Double = 0.0,
) {
    constructor(balanceListingEntity: BalanceListingEntity) : this(
        name = balanceListingEntity.name,
        balance = balanceListingEntity.balance
    )
}