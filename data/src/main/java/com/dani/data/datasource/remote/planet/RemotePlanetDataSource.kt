package com.dani.data.datasource.remote.planet

import com.dani.model.dto.Planet

interface RemotePlanetDataSource {
    suspend fun searchPlanet(searchParam: String): Planet
}