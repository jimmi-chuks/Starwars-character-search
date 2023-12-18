package com.dani.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val count: Int? = 0,
    val next: String? = "",
    val previous: String? = "",
    val results: List<Character> = emptyList()
)