package com.example.currencyformater.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_table")
data class UserTransactionsEntity(
    @PrimaryKey(autoGenerate = false)
    val date : String = "",
    val times : Int = 0
)
