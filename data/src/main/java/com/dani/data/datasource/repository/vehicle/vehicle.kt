package com.dani.data.datasource.repository.vehicle

import com.dani.model.dto.Vehicle

interface VehicleRepository{
    suspend fun getVehicle(id:String): Vehicle
}