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

    @Query("SELECT * FROM balances_table ORDER BY name ASC")
    fun fetchAllBalances(): Flow<MutableList<BalanceListingEntity>>

    @Query("SELECT EXISTS(SELECT * FROM balances_table WHERE name = :name)")
    fun hasThisCurrency(name : String) : Boolean
}