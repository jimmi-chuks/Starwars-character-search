package com.dani.data.datasource.remote.specie

import com.dani.model.dto.Specie

interface RemoteSpecieDataSource {
    suspend fun getSpecieDetails(id: String): Specie
}