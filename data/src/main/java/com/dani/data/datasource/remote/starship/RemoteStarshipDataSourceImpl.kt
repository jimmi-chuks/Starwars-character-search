package com.dani.data.datasource.remote.starship

import com.dani.data.datasource.remote.StarWarsApiService
import com.dani.model.dto.Starship
import javax.inject.Inject

class RemoteStarshipDataSourceImpl @Inject constructor(val starWarsApiService: StarWarsApiService) :
    RemoteStarshipDataSource {

    override suspend fun getStarship(id: String): Starship =
        starWarsApiService.getStarshipDetails(id)

}