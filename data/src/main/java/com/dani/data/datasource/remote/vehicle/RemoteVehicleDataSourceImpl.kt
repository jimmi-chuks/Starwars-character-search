package com.dani.data.datasource.remote.vehicle

import com.dani.data.datasource.remote.StarWarsApiService
import com.dani.model.dto.Vehicle
import javax.inject.Inject


class RemoteVehicleDataSourceImpl @Inject constructor(val apiService: StarWarsApiService) :
    RemoteVehicleDataSource {

    override suspend fun getVehicle(id: String): Vehicle =
        apiService.getVehicleDetails(id)
    
}