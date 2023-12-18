package com.dani.data.datasource.repository.planet

import com.dani.model.dto.Planet

interface PlanetRepository {
    suspend fun searchPlanet(searchParam: String): Planet
}
