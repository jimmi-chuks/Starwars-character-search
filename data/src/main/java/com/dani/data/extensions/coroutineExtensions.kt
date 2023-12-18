package com.dani.data.extensions

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext



suspend inline fun <I, reified O : Any> List<I>.parallelFetchList(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend (i: I) -> O
):List<O> = coroutineScope {
    map { async(coroutineContext) { block(it) } }.awaitAll()
}


