package com.example.currencyformater.domain.use_case

import com.example.currencyformater.data.local.BalancesDao
import com.example.currencyformater.data.local.GeneralDatabase
import com.example.currencyformater.data.local.TransactionsDao
import com.example.currencyformater.domain.respository.CurrencyRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainUseCaseTest {

    @Mock
    lateinit var respository: CurrencyRepository

    @Mock
    lateinit var generalDatabase: GeneralDatabase

    @Mock
    lateinit var balancesDao: BalancesDao

    @Mock
    lateinit var transactionDao: TransactionsDao

    private lateinit var mainUseCase: MainUseCase

    @Before
    fun setUp() {
        `when`(generalDatabase.balancesDao).thenReturn(balancesDao)
        `when`(generalDatabase.transactionsDao).thenReturn(transactionDao)
        mainUseCase = MainUseCase(respository, generalDatabase)
    }

}