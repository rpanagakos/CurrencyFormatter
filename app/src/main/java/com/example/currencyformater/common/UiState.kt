package com.example.currencyformater.common

sealed class UiState<T> {
    class Success<T>(val data: T?): UiState<T>()
    class Error<T>(val message: String,val data: T? = null): UiState<T>()
    class Loading<T>(val isLoading: Boolean = true): UiState<T>()
}