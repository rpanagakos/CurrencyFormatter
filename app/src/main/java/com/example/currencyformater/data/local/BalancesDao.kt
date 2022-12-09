package com.example.currencyformater.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BalancesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalance(balanceListingEntity: BalanceListingEntity)

    @Query("SELECT * FROM balances_table ORDER BY id ASC")
    fun fetchAllBalances(): MutableList<BalanceListingEntity>
}