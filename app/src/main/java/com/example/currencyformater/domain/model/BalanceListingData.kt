package com.example.currencyformater.domain.model

import com.example.currencyformater.data.local.BalanceListingEntity

data class BalanceListingData(
    val name: String = "",
    val balance: Double = 0.0,
) {
    constructor(balanceListingEntity: BalanceListingEntity) : this(
        name = balanceListingEntity.name,
        balance = balanceListingEntity.balance
    )
}