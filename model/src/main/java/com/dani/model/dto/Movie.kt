package com.dani.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val characters: List<String> = emptyList(),
    val created: String,
    val director: String,
    val edited: String?,
    @SerialName("episode_id")
    val episodeId: Int?,
    @SerialName("opening_crawl")
    val openingCrawl: String,
    val planets: List<String> = emptyList(),
    val producer: String,
    @SerialName("release_date")
    val releaseDate: String,
    val species: List<String> = emptyList(),
    val starships: List<String> = emptyList(),
    val title: String,
    val url: String,
    val vehicles: List<String> = emptyList()
)