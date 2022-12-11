package com.example.currencyformater.presentation.main_screen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.currencyformater.UnconfinedCoroutineRule
import com.example.currencyformater.common.fees.TransactionFee
import com.example.currencyformater.common.preferences.Preferences
import com.example.currencyformater.domain.use_case.MainUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var unconfinedCoroutineRule = UnconfinedCoroutineRule()

    @Mock
    lateinit var mainUseCase: MainUseCase

    @Mock
    lateinit var transactionFee: TransactionFee

    @Mock
    lateinit var preferences: Preferences

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(mainUseCase, transactionFee, preferences)
    }

    @Test
    fun wefefewf() = runTest {
        val test = 4
        assertEquals(4, test)
    }


}