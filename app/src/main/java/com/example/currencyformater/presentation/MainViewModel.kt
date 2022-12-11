package com.example.currencyformater.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyformater.common.UiState
import com.example.currencyformater.common.fees.TransactionFee
import com.example.currencyformater.common.preferences.Preferences
import com.example.currencyformater.common.removeExtraDigits
import com.example.currencyformater.data.local.BalanceListingEntity
import com.example.currencyformater.data.local.UserTransactionsEntity
import com.example.currencyformater.domain.model.BalanceListingData
import com.example.currencyformater.domain.model.CurrencyRateData
import com.example.currencyformater.domain.use_case.CurrencyUseCase
import com.rdp.ghostium.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
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
    private var currentDate: String = "3223232"

    private var pollingJob: Job? = null

    private val _balancesList: MutableState<List<BalanceListingData>> = mutableStateOf(emptyList())
    val balancesList: State<List<BalanceListingData>> = _balancesList

    private val _currenciesRates: MutableState<List<CurrencyRateData>> = mutableStateOf(emptyList())
    val currenciesRates: State<List<CurrencyRateData>> = _currenciesRates

    private val _showDialog: MutableState<Boolean> = mutableStateOf(false)
    val showDialog: State<Boolean> = _showDialog

    private val _dialogMessage = MutableStateFlow<String>("")
    val dialogMessage = _dialogMessage.asStateFlow()

    private val _receiveCurrencies: MutableState<List<CurrencyRateData>> = mutableStateOf(
        listOf(
            CurrencyRateData("GBP", 0.864015),
            CurrencyRateData("EUR", 1.0),
            CurrencyRateData("AED", 3.845625),
            CurrencyRateData("AFN", 92.246443)

        )
    )
    val receiveCurrencies: State<List<CurrencyRateData>> = _receiveCurrencies

    private val _convertedAmount: MutableState<Double> = mutableStateOf(0.0)
    val convertedAmount: State<Double> = _convertedAmount

    init {
        isFirstTimeInTheApp()
        getUserBalances()
        //getRates()
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
        viewModelScope.launch {
            currencyUseCase.addMoneyToFirstUser(BalanceListingEntity(START_CURRENCY, INITIAL_BUDGET))
        }
    }

    private fun getUserBalances() {
        viewModelScope.launch {
            currencyUseCase.getBalances.collect {
                _balancesList.value = it.map { entity -> BalanceListingData(entity) }
            }
        }
    }

    private fun getRates() {
        pollingJob?.cancel()
        currencyUseCase.getRates(baseCurrency).onEach { result ->
            when (result) {
                is UiState.Success -> {
                    pollingJob = rescheduleNextCall(TimeUnit.MINUTES.toMillis(1), ::getRates)
                    if (result.data != null) {
                        currentDate = result.data.date
                        _currenciesRates.value = result.data.rates
                        _receiveCurrencies.value = listOf(CurrencyRateData(baseCurrency, 1.0)) + result.data.rates
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

    fun convertOnFlyTheAmount(amount: String, fromCurrency: CurrencyRateData, toCurrency: CurrencyRateData) {
        if (amount.isEmpty()) {
            _convertedAmount.value = 0.0
            return
        }
        viewModelScope.launch {
            delay(300)
            val newAmount = amount.toDouble() * toCurrency.rate
            _convertedAmount.value = newAmount
        }
    }

    fun submitConvert(amount: String, fromCurrency: CurrencyRateData, toCurrency: CurrencyRateData) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                if (fromCurrency.name == toCurrency.name) {
                    displayErrorMessage("You have same  currency in both fields: ${fromCurrency.name}")
                    return@launch
                }

                if (!currencyUseCase.hasThisCurrency(fromCurrency.name)) {
                    displayErrorMessage("You don't have available balance for " + fromCurrency.name)
                    return@launch
                }


                val euroRate =
                    if (fromCurrency.name == START_CURRENCY)
                        1.0
                    else
                        receiveCurrencies.value.find {
                            it.name == START_CURRENCY
                        }?.rate ?: 1.0

                val transactionsForToday =
                    if (currencyUseCase.hasTransactionsForToday(currentDate))
                        getTheTransactionsForToday()
                    else
                        0
                val amountWithFee = amount.toDouble() + transactionFee.calculateTheFinalTransactionFee(amount.toDouble(), euroRate, transactionsForToday)
                //need to check if it has the correct balance after the commission
                val balanceFromCurrency = balancesList.value.firstOrNull() { it.name == fromCurrency.name }?.balance ?: 0.0
                val finalAmount = balanceFromCurrency - amountWithFee
                if (finalAmount > 0) {
                    //save the new currency  + old balance of it if it has
                    updateReceivedCurrency(toCurrency.name)
                    // remove the amount  from fromCurrency
                    removeTheTotalAmountFromChosenCurrency(finalAmount, fromCurrency.name)
                    //plus +1 the transaction
                    addOneMoreTransactionForToday(transactionsForToday + 1)
                } else {
                    //error message
                    displayErrorMessage("You don't have enough money to ${fromCurrency.name} balance")
                }

            }
        } catch (e: java.lang.Exception) {
            displayErrorMessage("Something went wrong! Please check the amount you entered")
        }
    }

    private fun displayErrorMessage(errorMessage: String) {
        viewModelScope.launch {
            _dialogMessage.emit(errorMessage)
            changeDialogStatus()
        }
    }

    fun changeDialogStatus() {
        _showDialog.value = !showDialog.value
    }

    private suspend fun addOneMoreTransactionForToday(totalTransaction: Int) {
        currencyUseCase.addOneMoreTransactionForToday(UserTransactionsEntity(currentDate, totalTransaction))
    }

    private suspend fun updateReceivedCurrency(currencyName: String) {
        val oldBalance = balancesList.value.find { it.name == currencyName }?.balance ?: 0.0
        currencyUseCase.addMoneyToFirstUser(BalanceListingEntity(currencyName, (oldBalance + convertedAmount.value).removeExtraDigits()))
    }

    private suspend fun removeTheTotalAmountFromChosenCurrency(finalAmount: Double, currencyName: String) {
        currencyUseCase.addMoneyToFirstUser(BalanceListingEntity(currencyName, finalAmount.removeExtraDigits()))
    }

    private suspend fun getTheTransactionsForToday(): Int {
        val job = viewModelScope.async {
            return@async currencyUseCase.getTransactionsForToday(currentDate)
        }
        return job.await()
    }

    //this probably should move to an abstract class
    private fun rescheduleNextCall(repeatMillis: Long, call: () -> Unit): Job {
        return viewModelScope.launch {
            delay(repeatMillis)
            call.invoke()
        }
    }
}