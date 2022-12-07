package com.example.currencyformater.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyformater.common.UiState
import com.example.currencyformater.domain.use_case.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase
) : ViewModel() {

    fun getRates(){
        currencyUseCase.getRates().onEach { result ->
            when(result){
                is UiState.Success -> {
                    println(result.data)
                }
                is UiState.Error -> {}
                is UiState.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }
}