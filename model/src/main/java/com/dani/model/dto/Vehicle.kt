package com.dani.model.dto

import kotlinx.serialization.SerialName

data class Vehicle(
    @SerialName("cargo_capacity")
    val cargoCapacity: String?,
    val consumables: String?,
    @SerialName("cost_in_credits")
    val costInCredits: String?,
    val created: String?,
    val crew: String?,
    val edited: String?,
    val length: String?,
    val manufacturer: String?,
    @SerialName("max_atmosphering_speed")
    val maxAtmospheringSpeed: String?,
    val model: String?,
    val name: String?,
    val passengers: String?,
    val pilots: List<Any>?,
    val films: List<String>?,
    val url: String?,
    @SerialName("vehicle_class")
    val vehicleClass: String?
)