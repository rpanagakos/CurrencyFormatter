package com.example.currencyformater.common.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.currencyformater.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesIml @Inject constructor(
    @ApplicationContext context: Context
) : Preferences {

    companion object {
        const val TRANSACTIONS = "transactions"
        const val FIRST_INIT_APP = "initApp"
    }

    private val preferences: SharedPreferences = context.getSharedPreferences(
        Constants.SHARED_PREF,
        Context.MODE_PRIVATE
    )

    override fun addOneMoreFreeTransaction() {
        val updatedFreeTransactions = getTransactionsFromShared() + 1
        preferences.edit().putInt(TRANSACTIONS, updatedFreeTransactions).apply()
    }

    override fun getTransactionsFromShared(): Int {
        return preferences.getInt(TRANSACTIONS, 0) ?: 0
    }

    override fun firstStart(): Boolean {
        val isFirstTime = preferences.getBoolean(FIRST_INIT_APP, true) ?: true
        if (!isFirstTime)
            return isFirstTime
        updateInitApp()
        return isFirstTime
    }

    private fun updateInitApp() {
        preferences.edit().putBoolean(FIRST_INIT_APP, false).apply()
    }
}