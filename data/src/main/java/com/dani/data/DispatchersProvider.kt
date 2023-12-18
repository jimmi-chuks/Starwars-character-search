package com.dani.data

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class DispatchersProvider @Inject constructor(
    val main: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val io: CoroutineDispatcher
)