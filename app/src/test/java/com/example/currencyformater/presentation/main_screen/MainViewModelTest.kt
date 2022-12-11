package com.example.currencyformater.presentation.main_screen

import com.example.currencyformater.common.DispatcherProvider
import com.example.currencyformater.common.fees.TransactionFee
import com.example.currencyformater.common.preferences.Preferences
import com.example.currencyformater.data.local.BalanceListingEntity
import com.example.currencyformater.data.local.UserTransactionsEntity
import com.example.currencyformater.domain.model.CurrencyRateData
import com.example.currencyformater.domain.use_case.MainUseCase
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private var autCloseable: AutoCloseable? = null

    private lateinit var dispatcherProvider: DispatcherProvider

    @Mock
    lateinit var mainUseCase: MainUseCase

    @Mock
    lateinit var transactionFee: TransactionFee

    @Mock
    lateinit var preferences: Preferences

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        dispatcherProvider = DispatcherProvider(
            mainDispatcher = StandardTestDispatcher(),
            iODispatcher = StandardTestDispatcher(),
            defaultDispatcher = StandardTestDispatcher()
        )
        autCloseable = MockitoAnnotations.openMocks(this)
        mainViewModel = MainViewModel(mainUseCase, transactionFee, preferences, dispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        autCloseable?.close()
    }

    @Test
    fun `check if after the base currency change it recalls the rates`() = runTest {
        mainViewModel.changeBaseCurrency("AUD")
        runCurrent()
        verify(mainUseCase, times(1)).getRates("AUD")
    }

    @Test
    fun `convert the amount with empty value`() = runTest {
        val currencyRateData = CurrencyRateData("EUR", 1.0)
        mainViewModel.convertOnFlyTheAmount("", currencyRateData, currencyRateData)
        runCurrent()
        val amount = mainViewModel.convertedAmount.value
        assertEquals(0.0, amount, 0.0)
    }


    @Test
    fun `convert the amount with  totally wrong amount`() = runTest {
        val currencyRateData = CurrencyRateData("EUR", 1.0)
        mainViewModel.convertOnFlyTheAmount("fwfefw", currencyRateData, currencyRateData)
        runCurrent()
        val dialogMessage = mainViewModel.dialogMessage.value
        val showDialog = mainViewModel.showDialog.value
        assertEquals("Something went wrong! Please check the amount you entered", dialogMessage)
        Truth.assertThat(showDialog).isTrue()
    }

    @Test
    fun `convert the amount with  totally correct input`() = runTest {
        val currencyRateData = CurrencyRateData("EUR", 1.0)
        val currencyRateData2 = CurrencyRateData("EUR", 2.0)
        mainViewModel.convertOnFlyTheAmount("10.0", currencyRateData, currencyRateData2)
        runCurrent()
        val amount = mainViewModel.convertedAmount.value
        assertEquals(20.0, amount, 0.0)
    }

    @Test
    fun `submit the conversion with the same currencies`() = runTest() {
        val currencyRateData = CurrencyRateData("EUR", 1.0)
        mainViewModel.submitConvert("", currencyRateData, currencyRateData)
        runCurrent()
        val dialogMessage = mainViewModel.dialogMessage.value
        val showDialog = mainViewModel.showDialog.value
        assertEquals("You have same  currency in both fields: EUR", dialogMessage)
        Truth.assertThat(showDialog).isTrue()
    }

    @Test
    fun `submit the conversion without balance of sell currency`() = runTest() {
        val currencyRateData = CurrencyRateData("EUR", 1.0)
        val currencyRateDat2 = CurrencyRateData("AUD", 1.4)
        Mockito.`when`(mainUseCase.hasThisCurrency(currencyRateData.name)).thenReturn(false)
        mainViewModel.submitConvert("", currencyRateData, currencyRateDat2)
        runCurrent()
        val dialogMessage = mainViewModel.dialogMessage.value
        val showDialog = mainViewModel.showDialog.value
        assertEquals("You don't have available balance for EUR", dialogMessage)
        Truth.assertThat(showDialog).isTrue()
    }

    @Test
    fun `submit the conversion with zero balance of sell currency`() = runTest() {
        val currencyRateData = CurrencyRateData("EUR", 1.0)
        val currencyRateDat2 = CurrencyRateData("AUD", 1.4)
        Mockito.`when`(mainUseCase.hasThisCurrency(currencyRateData.name)).thenReturn(true)
        Mockito.`when`(transactionFee.calculateTheFinalTransactionFee(10.0, 1.0, 0)).thenReturn(0.0)
        mainViewModel.submitConvert("10.0", currencyRateData, currencyRateDat2)
        runCurrent()
        val dialogMessage = mainViewModel.dialogMessage.value
        val showDialog = mainViewModel.showDialog.value
        assertEquals("You don't have enough money to EUR balance", dialogMessage)
        Truth.assertThat(showDialog).isTrue()
    }

    @Test
    fun `submit the conversion with  balance of sell currency`() = runTest() {

        val balanceFlow = flow<List<BalanceListingEntity>> {
            emit(listOf(BalanceListingEntity("EUR", 100000.0)))
        }

        assertNotNull(balanceFlow)
        mainUseCase.stub {
            onBlocking { getBalances }.doReturn(balanceFlow)
        }


        var list = emptyList<BalanceListingEntity>()

        val job = launch {
            mainUseCase.getBalances.collectLatest {
                list = it
            }
        }

        runCurrent()

        mainViewModel.onResume()

        advanceTimeBy(100)

        Truth.assertThat(list.size).isEqualTo(1)
        val balance = mainViewModel.balancesList.value
        assertNotNull(balance)

        val currencyRateData = CurrencyRateData("EUR", 1.0)
        val currencyRateDat2 = CurrencyRateData("AUD", 1.4)
        Mockito.`when`(mainUseCase.hasThisCurrency(currencyRateData.name)).thenReturn(true)
        mainViewModel.submitConvert("10.0", currencyRateData, currencyRateDat2)
        runCurrent()
        verify(mainUseCase, times(1)).updateCustomerBalance(BalanceListingEntity(name = currencyRateData.name, balance = 99990.0))
        verify(mainUseCase, times(1)).addOneMoreTransactionForToday(UserTransactionsEntity(date = "", times = 1))
        val dialogMessage = mainViewModel.dialogMessage.value
        val showDialog = mainViewModel.showDialog.value
        assertEquals("You transaction has been completed. Your new balance is 99990.0 EUR", dialogMessage)
        Truth.assertThat(showDialog).isTrue()
        job.cancel()
    }


}