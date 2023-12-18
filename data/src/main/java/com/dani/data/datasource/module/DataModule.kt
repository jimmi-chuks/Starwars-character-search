package com.dani.data.datasource.module

import com.dani.data.datasource.local.module.LocalDataSourceModule
import com.dani.data.datasource.local.module.RoomDataModule
import com.dani.data.datasource.remote.module.NetworkModule
import com.dani.data.datasource.remote.module.RemoteDataSourceModule
import com.dani.data.datasource.repository.RepositoryModule
import dagger.Module

@Module(
    includes = [
        RoomDataModule::class,
        LocalDataSourceModule::class,
        NetworkModule::class,
        RemoteDataSourceModule::class,
        RepositoryModule::class
    ]
)
class DatabaseModule
