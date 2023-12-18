package com.dani.domain.di

import com.dani.data.DispatchersProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class DomainModule {

    @Provides
    fun providesIODispatchers() = Dispatchers.IO

    @Provides
    fun providesDispatchers() =
        DispatchersProvider(Dispatchers.Main, Dispatchers.Default, Dispatchers.IO)


}