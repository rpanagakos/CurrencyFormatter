package com.example.currencyformater.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balances_table")
data class BalanceListingEntity(
    val name: String,
    val balance: Double,
    @PrimaryKey val id: Int? = null
)
