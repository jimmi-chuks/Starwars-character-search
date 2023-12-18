package com.dani.model.dto

data class SearchError(val errorMessage: String)
data class SearchViewState(
    val searchDataCount: Int = 0,
    val nextPageUrl: String = "",
    val characters: List<Character>? = null,
    val searchLoading: Boolean = true,
    val searchError: SearchError? = null,
    val nextPageLoading: Boolean = false,
    val currentSearchParam: String = "",
    val emptySearchError: Boolean =
        characters?.isNullOrEmpty() ?: false && searchError != null
)

data class  CharacterDetails(
    val name: String = "",
    val birthYear: String = "",
    val height: Double? = null,
    val language: String = "",
    val specieNames: List<String>? = null,
    val homeworld: String = "",
    val population: String? = null,
    val featuredMovies: List<Movie> = emptyList(),
    val isDefaultValues: Boolean = true
)


