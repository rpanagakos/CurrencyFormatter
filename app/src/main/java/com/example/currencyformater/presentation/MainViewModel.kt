package com.example.currencyformater.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyformater.common.UiState
import com.example.currencyformater.common.fees.TransactionFee
import com.example.currencyformater.common.preferences.Preferences
import com.example.currencyformater.data.local.BalanceListingEntity
import com.example.currencyformater.domain.model.BalanceListingData
import com.example.currencyformater.domain.model.CurrencyRateData
import com.example.currencyformater.domain.use_case.CurrencyUseCase
import com.rdp.ghostium.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase,
    private val transactionFee: TransactionFee,
    private val preferences: Preferences,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _balancesList = MutableStateFlow<List<BalanceListingData>>(emptyList())
    val balancesList = _balancesList.asStateFlow()

    private val _currenciesRates = MutableStateFlow<List<CurrencyRateData>>(emptyList())
    val currenciesRates = _currenciesRates.asStateFlow()

    init {
        isFirstTimeInTheApp()
        getUserBalances()
        getRates()
    }

    private fun isFirstTimeInTheApp() {
        val isFirstTime = preferences.firstStart()
        if (isFirstTime) {
            addAppGiftMoneyDueToXmas()
            displayWalkthrough()
        }
    }

    private fun displayWalkthrough() {}

    private fun addAppGiftMoneyDueToXmas() {
        viewModelScope.launch(defaultDispatcher) {
            currencyUseCase.addMoneyToFirstUser(BalanceListingEntity("EUR", 1000.0))
        }
    }

    private fun getUserBalances() {
        currencyUseCase.getBalances().onEach { result ->
            when (result) {
                is UiState.Success -> {
                    if (result.data != null)
                        _balancesList.emit(result.data)
                }
                else -> return@onEach
            }
        }
    }

    private fun getRates() {
        currencyUseCase.getRates().onEach { result ->
            when (result) {
                is UiState.Success -> {
                    rescheduleNextCall(TimeUnit.MINUTES.toMillis(1), ::getRates)
                    if (result.data != null)
                        _currenciesRates.emit(result.data.rates)
                }
                is UiState.Error -> {}
                is UiState.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }


    //this probably should move to an abstract class
    private fun rescheduleNextCall(repeatMillis : Long, call : () -> Unit) : Job {
        return viewModelScope.launch {
            delay(repeatMillis)
            call.invoke()
        }
    }
}