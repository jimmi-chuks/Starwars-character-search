package com.dani.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Character(
    val name: String,
    @SerialName("birth_year")
    val birthYear: String,
    val height: String,
    val species: List<String>?,
    @SerialName("eye_color")
    val eyeColor: String,
    val films: List<String>,
    val gender: String?,
    @SerialName("hair_color")
    val hairColor: String?,
    @SerialName("homeworld")
    val homeWorld: String?,
    val mass: String?,
    @SerialName("skin_color")
    val skinColor: String?,
    val created: String?,
    val edited: String?,
    val starships: List<String>?,
    val url: String,
    val vehicles: List<String>?
)