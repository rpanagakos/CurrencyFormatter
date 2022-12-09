package com.example.currencyformater.data.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions_table WHERE date = :date")
    suspend fun getTransactionsForToday(date: String): UserTransactionsEntity
}