package com.dani.data.datasource.repository.starship

import com.dani.model.dto.Starship

interface StarshipRepository {
    suspend fun getStarship(id: String): Starship
}
