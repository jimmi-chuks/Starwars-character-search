package com.dani.data.datasource.remote.module

import com.dani.data.datasource.remote.character.RemoteCharacterDataSource
import com.dani.data.datasource.remote.character.RemoteCharacterDataSourceImpl
import com.dani.data.datasource.remote.movie.RemoteMovieDataSource
import com.dani.data.datasource.remote.movie.RemoteMovieDataSourceImpl
import com.dani.data.datasource.remote.planet.RemotePlanetDataSource
import com.dani.data.datasource.remote.planet.RemotePlanetDataSourceImpl
import com.dani.data.datasource.remote.specie.RemoteSpecieDataSource
import com.dani.data.datasource.remote.specie.RemoteSpecieDataSourceImpl
import com.dani.data.datasource.remote.starship.RemoteStarshipDataSource
import com.dani.data.datasource.remote.starship.RemoteStarshipDataSourceImpl
import com.dani.data.datasource.remote.vehicle.RemoteVehicleDataSource
import com.dani.data.datasource.remote.vehicle.RemoteVehicleDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindRemoteCharacterDataSource(remoteCharacterDataSourceImpl: RemoteCharacterDataSourceImpl): RemoteCharacterDataSource

    @Binds
    abstract fun bindRemoteMovieDataSource(remoteMovieDataSourceImpl: RemoteMovieDataSourceImpl): RemoteMovieDataSource

    @Binds
    abstract fun bindRemotePlanetDataSource(remotePlanetDataSourceImpl: RemotePlanetDataSourceImpl): RemotePlanetDataSource

    @Binds
    abstract fun bindRemoteSpecieDataSource(remoteSpecieDataSourceImpl: RemoteSpecieDataSourceImpl): RemoteSpecieDataSource

    @Binds
    abstract fun bindRemoteStarshipDataSource(remoteStarshipDataSourceImpl: RemoteStarshipDataSourceImpl): RemoteStarshipDataSource

    @Binds
    abstract fun bindRemoteVehicleDataSource(remoteVehicleDataSourceImpl: RemoteVehicleDataSourceImpl): RemoteVehicleDataSource
}