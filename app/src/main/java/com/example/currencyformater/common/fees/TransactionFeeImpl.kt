package com.example.currencyformater.common.fees

import android.content.Context
import android.content.SharedPreferences
import com.example.currencyformater.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionFeeImpl @Inject constructor(
    @ApplicationContext context: Context
    //the room database
) : TransactionFee {

    companion object {
        const val TRANSACTIONS = "transactions"
        const val FREE_TRANSACTIONS = 5
        const val NONE_FEE = 0.0
    }

    private val preferences: SharedPreferences = context.getSharedPreferences(
        Constants.SHARED_PREF,
        Context.MODE_PRIVATE
    )


    override fun calculateTheFinalTransactionFee(amount: Double, currencyRate: Double): Double {
        if (!hasUserExceededFreeTransaction()) {
            addOneMoreFreeTransaction()
            return NONE_FEE
        }

        val transactionCommission =
            TransactionCommission.getFromTransactionTimes(getTransactionsForToday())
        val fee = ((amount * transactionCommission.commissionRate) / 100)
        val extraFee = transactionCommission.extraCommissionRate * currencyRate
        return fee + extraFee
        /*if (userHasCompletedAbove15Transactions()) {
            feeRate = 1.2
            extraFee = 0.3 * currencyRate
        } else {
            feeRate = 0.7
        }
        return ((amount * feeRate) / 100) + extraFee*/
    }


    private fun hasUserExceededFreeTransaction(): Boolean {
        return getTransactionsFromShared() > FREE_TRANSACTIONS
    }

    private fun addOneMoreFreeTransaction() {
        val updatedFreeTransactions = getTransactionsFromShared() + 1
        preferences.edit().putInt(TRANSACTIONS, updatedFreeTransactions).apply()
    }

    private fun getTransactionsFromShared(): Int {
        return preferences.getInt(TRANSACTIONS, 0) ?: 0
    }

    private fun getTransactionsForToday(): Int = 0

    enum class TransactionCommission(
        val value: Int,
        val commissionRate: Double,
        val extraCommissionRate: Double
    ) {
        NORMAL(15, 0.7, 0.0),
        EXTRA(Int.MAX_VALUE, 1.2, 0.3);

        companion object {
            fun getFromTransactionTimes(value: Int) = values().first { it.value <= value }
        }
    }

}