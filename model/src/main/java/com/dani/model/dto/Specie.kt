package com.dani.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Specie(
    @SerialName("average_height")
    val averageHeight: String?,
    @SerialName("average_lifespan")
    val averageLifespan: String?,
    val classification: String?,
    val created: String?,
    val designation: String?,
    val edited: String?,
    @SerialName("eye_colors")
    val eyeColors: String = "N/A",
    @SerialName("hair_colors")
    val hairColors: String,
    @SerialName("homeworld")
    val homeWorld: String?,
    val language: String?,
    val name: String?,
    val people: List<String>?,
    val films: List<String>?,
    @SerialName("skin_colors")
    val skinColors: String?,
    val url: String?
)