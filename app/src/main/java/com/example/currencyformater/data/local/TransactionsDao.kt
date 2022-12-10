package com.example.currencyformater.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions_table WHERE date = :date")
    suspend fun getTransactionsForToday(date: String): UserTransactionsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransactions(userTransactionsEntity: UserTransactionsEntity)

    @Query("SELECT EXISTS(SELECT * FROM transactions_table WHERE date = :date)")
    fun hasTransactionsForToday(date : String) : Boolean
}