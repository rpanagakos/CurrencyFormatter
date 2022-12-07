package com.example.currencyformater.domain.use_case

import com.example.currencyformater.common.UiState
import com.example.currencyformater.domain.model.ExchangeRateData
import com.example.currencyformater.domain.respository.CurrencyRepository
import com.rdp.ghostium.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    fun getRates(): Flow<UiState<ExchangeRateData>> = flow {
        try {
            emit(UiState.Loading<ExchangeRateData>())
            val coins = repository.getLatestRates()
            emit(UiState.Success<ExchangeRateData>(ExchangeRateData(coins)))
        } catch(e: HttpException) {
            emit(UiState.Error<ExchangeRateData>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(UiState.Error<ExchangeRateData>("Couldn't reach server. Check your internet connection."))
        }
    }.flowOn(ioDispatcher)
}