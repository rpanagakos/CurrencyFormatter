package com.example.currencyformater.common.fees

import com.example.currencyformater.common.preferences.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionFeeImpl @Inject constructor(
    private val preferences: Preferences
) : TransactionFee {

    companion object {
        const val FREE_TRANSACTIONS = 5
        const val NONE_FEE = 0.0
    }

    override fun calculateTheFinalTransactionFee(
        amount: Double,
        currencyRate: Double,
        transactionsForToday: Int
    ): Double {
        if (!hasUserExceededFreeTransaction()) {
            preferences.addOneMoreFreeTransaction()
            return NONE_FEE
        }

        val transactionCommission =
            TransactionCommission.getFromTransactionTimes(transactionsForToday)
        val fee = ((amount * transactionCommission.commissionRate) / 100)
        val extraFee = transactionCommission.extraCommissionRate * currencyRate
        return fee + extraFee
    }

    private fun hasUserExceededFreeTransaction(): Boolean {
        return preferences.getTransactionsFromShared() >= FREE_TRANSACTIONS
    }


    enum class TransactionCommission(
        val value: Int,
        val commissionRate: Double,
        val extraCommissionRate: Double
    ) {
        NORMAL(15, 0.7, 0.0),
        EXTRA(Int.MAX_VALUE, 1.2, 0.3);

        companion object {
            fun getFromTransactionTimes(value: Int) = values().first { value <= it.value }
        }
    }

}