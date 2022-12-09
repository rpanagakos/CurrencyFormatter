package com.example.currencyformater.common.fees

interface TransactionFee {

    fun calculateTheFinalTransactionFee(amount : Double, currencyRate : Double, transactionsForToday : Int) : Double
}