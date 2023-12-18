package com.dani.data.datasource.remote.vehicle

import com.dani.model.dto.Vehicle

interface RemoteVehicleDataSource {
    suspend fun getVehicle(id: String): Vehicle
}