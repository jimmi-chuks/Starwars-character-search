package com.dani.model.dto

import kotlinx.serialization.SerialName

data class Starship(
    val mglt: String?,
    @SerialName("cargo_capacity")
    val cargoCapacity: String?,
    val consumables: String?,
    @SerialName("cost_in_credits")
    val costInCredits: String?,
    val created: String?,
    val crew: String?,
    val edited: String?,
    @SerialName("hyperdrive_rating")
    val hyperdriveRating: String?,
    val length: String?,
    val manufacturer: String?,
    @SerialName("max_atmosphering_speed")
    val maxAtmospheringSpeed: String?,
    val model: String?,
    val name: String?,
    val passengers: String?,
    val films: List<String>?,
    val pilots: List<Any>?,
    @SerialName("starship_class")
    val starshipClass: String?,
    val url: String?
)