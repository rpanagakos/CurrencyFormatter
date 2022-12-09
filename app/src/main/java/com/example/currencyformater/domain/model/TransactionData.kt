package com.example.currencyformater.domain.model

import com.example.currencyformater.data.local.UserTransactionsEntity

data class TransactionData(
    val date: String = "",
    val times: Int = 0
) {

    constructor(userTransactionsEntity: UserTransactionsEntity) : this(
        date = userTransactionsEntity.date,
        times = userTransactionsEntity.times
    )
}