package com.example.currencyformater.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BalanceListingEntity::class, UserTransactionsEntity::class],
    version = 1
)
abstract class GeneralDatabase : RoomDatabase(){

    abstract val balancesDao : BalancesDao
    abstract val transactionsDao : TransactionsDao
}