package com.example.currencyformater.common.preferences

interface Preferences {

    fun addOneMoreFreeTransaction()
    fun getTransactionsFromShared(): Int
    fun firstStart() : Boolean
}