package com.example.currencyformater.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Singleton
class DispatcherProvider(
    val mainDispatcher : CoroutineDispatcher = Dispatchers.Main,
    val iODispatcher: CoroutineDispatcher = Dispatchers.IO,
    val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
)