package com.example.currencyformater.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    companion object {
        const val START_CURRENCY = "EUR"
        const val INITIAL_BUDGET = 1000.0
    }

    private var baseCurrency = START_CURRENCY

    private val _balancesList = MutableStateFlow<List<BalanceListingData>>(emptyList())
    val balancesList = _balancesList.asStateFlow()

    private val _currenciesRates = MutableStateFlow<List<CurrencyRateData>>(emptyList())
    val currenciesRates = _currenciesRates.asStateFlow()

    private val _receiveCurrencies: MutableState<List<CurrencyRateData>> = mutableStateOf(listOf())
    val receiveCurrencies: State<List<CurrencyRateData>> = _receiveCurrencies

    private val _amountWithoutCommission: MutableState<Double> = mutableStateOf(0.0)
    val amountWithoutCommission: State<Double> = _amountWithoutCommission

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
            currencyUseCase.addMoneyToFirstUser(BalanceListingEntity(START_CURRENCY, INITIAL_BUDGET))
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
        currencyUseCase.getRates(baseCurrency).onEach { result ->
            when (result) {
                is UiState.Success -> {
                    rescheduleNextCall(TimeUnit.MINUTES.toMillis(1), ::getRates)
                    if (result.data != null) {
                        _currenciesRates.emit(result.data.rates)
                        _receiveCurrencies.value = result.data.rates
                    }
                }
                is UiState.Error -> {}
                is UiState.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun changeBaseCurrency(currencyName: String) {
        baseCurrency = currencyName
        getRates()
    }

    fun convertOnFlyTheAmount(amount: Double, fromCurrency: CurrencyRateData, toCurrency: CurrencyRateData) {
        val newAmount = amount * toCurrency.rate
        _amountWithoutCommission.value = newAmount
    }

    fun submitConvert(amount: Double, fromCurrency: CurrencyRateData, toCurrency: CurrencyRateData) {
        //need to check if he has this currency in his bank otherwise print
        if (!currencyUseCase.hasThisCurrency(fromCurrency.name))
            return
        //calculate the new balances without the commission
        //probably the same with above one

        //final amount with the commision
        val euroRate =
            if (fromCurrency.name == START_CURRENCY)
                1.0
            else
                receiveCurrencies.value.find {
                    it.name == START_CURRENCY
                }?.rate ?: 1.0

        val finalAmount = transactionFee.calculateTheFinalTransactionFee(amount, euroRate, 15)
        //need to check if it has the correct balance after the commission

        //submit and save to db

    }


    //this probably should move to an abstract class
    private fun rescheduleNextCall(repeatMillis: Long, call: () -> Unit): Job {
        return viewModelScope.launch {
            delay(repeatMillis)
            call.invoke()
        }
    }
}