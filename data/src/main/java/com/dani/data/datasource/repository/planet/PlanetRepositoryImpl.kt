package com.dani.data.datasource.repository.planet

import com.dani.data.datasource.remote.planet.RemotePlanetDataSource
import com.dani.model.dto.Planet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PlanetRepositoryImpl @Inject constructor(
    private val remotePlanetDataSource: RemotePlanetDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PlanetRepository {

    override suspend fun searchPlanet(searchParam: String): Planet =
        withContext(ioDispatcher) { remotePlanetDataSource.searchPlanet(searchParam) }
}
