package com.dani.data.datasource.remote.starship

import com.dani.model.dto.Starship

interface RemoteStarshipDataSource {
    suspend fun getStarship(id: String): Starship
}