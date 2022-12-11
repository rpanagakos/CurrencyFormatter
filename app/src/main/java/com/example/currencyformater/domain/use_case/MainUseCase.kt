package com.example.currencyformater.domain.use_case

import com.example.currencyformater.common.UiState
import com.example.currencyformater.common.annotations.OpenClassTesting
import com.example.currencyformater.data.local.BalanceListingEntity
import com.example.currencyformater.data.local.GeneralDatabase
import com.example.currencyformater.data.local.UserTransactionsEntity
import com.example.currencyformater.domain.model.ExchangeRateData
import com.example.currencyformater.domain.respository.CurrencyRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ViewModelScoped
@OpenClassTesting
class MainUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    private val generalDatabase: GeneralDatabase
) {

    private val balancesDao = generalDatabase.balancesDao
    private val transactionsDao = generalDatabase.transactionsDao

    fun getRates(baseCurrency : String): Flow<UiState<ExchangeRateData>> = flow {
        try {
            emit(UiState.Loading<ExchangeRateData>())
            val coins = repository.getLatestRates(baseCurrency)
            emit(UiState.Success<ExchangeRateData>(ExchangeRateData(coins)))
        } catch (e: HttpException) {
            emit(UiState.Error<ExchangeRateData>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(UiState.Error<ExchangeRateData>("Couldn't reach server. Check your internet connection."))
        }
    }

    suspend fun updateCustomerBalance(balanceListingEntity: BalanceListingEntity) {
        balancesDao.insertBalance(balanceListingEntity)
    }

    val getBalances : Flow<List<BalanceListingEntity>> = balancesDao.fetchAllBalances()

    fun hasThisCurrency(name : String): Boolean {
       return balancesDao.hasThisCurrency(name)
    }

    fun hasTransactionsForToday(date: String): Boolean {
        return transactionsDao.hasTransactionsForToday(date)
    }

    suspend fun getTransactionsForToday(date : String) : Int {
        val response = transactionsDao.getTransactionsForToday(date)
        return response?.times ?: 0
    }

    suspend fun addOneMoreTransactionForToday(transactionsEntity: UserTransactionsEntity){
        transactionsDao.updateTransactions(transactionsEntity)
    }
}