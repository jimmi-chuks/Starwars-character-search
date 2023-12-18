package com.dani.data.datasource.repository.specie

import com.dani.model.dto.Specie

interface SpecieRepository {
    suspend fun getSpecieDetails(id:String): Specie
}