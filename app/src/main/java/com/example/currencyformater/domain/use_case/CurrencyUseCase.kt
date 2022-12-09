package com.example.currencyformater.domain.use_case

import com.example.currencyformater.common.UiState
import com.example.currencyformater.data.local.BalanceListingEntity
import com.example.currencyformater.data.local.GeneralDatabase
import com.example.currencyformater.domain.model.BalanceListingData
import com.example.currencyformater.domain.model.ExchangeRateData
import com.example.currencyformater.domain.respository.CurrencyRepository
import com.rdp.ghostium.di.IoDispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ViewModelScoped
class CurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    private val generalDatabase: GeneralDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    private val balancesDao = generalDatabase.balancesDao

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
    }.flowOn(ioDispatcher)

    suspend fun addMoneyToFirstUser(balanceListingEntity: BalanceListingEntity) {
        balancesDao.insertBalance(balanceListingEntity)
    }

    fun getBalances(): Flow<UiState<List<BalanceListingData>>> = flow {
        emit(UiState.Loading<List<BalanceListingData>>())
        val response = balancesDao.fetchAllBalances()
        emit(UiState.Success<List<BalanceListingData>>(
            data = response.map { BalanceListingData(it) } ?: emptyList()
        ))
    }.flowOn(ioDispatcher)

    fun hasThisCurrency(name : String): Boolean {
       return balancesDao.hasThisCurrency(name)
    }
}