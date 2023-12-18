package com.dani.data.datasource.local.module

import com.dani.data.datasource.local.recentsearches.RecentSearchLocalDataSource
import com.dani.data.datasource.local.recentsearches.RecentSearchLocalDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindRecentSearchLocalDataSource(dataSource: RecentSearchLocalDataSourceImpl): RecentSearchLocalDataSource
}