package com.dani.data.datasource.remote.planet

import com.dani.data.datasource.remote.StarWarsApiService
import com.dani.model.dto.Planet
import javax.inject.Inject

class RemotePlanetDataSourceImpl @Inject constructor(val starWarsApiService: StarWarsApiService) :
    RemotePlanetDataSource {

    override suspend fun searchPlanet(searchParam: String): Planet =
        starWarsApiService.getPlanetDetails(searchParam)

}