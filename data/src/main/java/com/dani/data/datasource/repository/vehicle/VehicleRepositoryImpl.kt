package com.dani.data.datasource.repository.vehicle

import com.dani.data.datasource.remote.vehicle.RemoteVehicleDataSource
import com.dani.model.dto.Vehicle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val remoteVehicleDataSource: RemoteVehicleDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : VehicleRepository {

    override suspend fun getVehicle(id: String): Vehicle =
        withContext(ioDispatcher) { remoteVehicleDataSource.getVehicle(id) }

}