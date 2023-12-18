package com.dani.data.datasource.remote.specie

import com.dani.data.datasource.remote.StarWarsApiService
import com.dani.model.dto.Specie
import javax.inject.Inject

class RemoteSpecieDataSourceImpl @Inject constructor(val apiService: StarWarsApiService) :
    RemoteSpecieDataSource {

    override suspend fun getSpecieDetails(id: String): Specie =
        apiService.getSpecieDetails(id)

}