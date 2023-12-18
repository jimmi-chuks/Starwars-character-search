package com.dani.domain.usecases

import com.dani.model.dto.Character
import com.dani.model.dto.Movie
import com.dani.model.dto.Planet
import com.dani.model.dto.Specie

data class CharacterDetails(
    val name: String,
    val gender: String,
    val height: String,
    val birthYear: String,
    val hairColor: String,
    val eyeColor: String,
    val planetData: PlanetData?,
    val speciesData: List<SpeciesData>,
    val featuredEpisodes: List<MovieData>
)

data class VehicleData(
    val name: String,
    val model: String,
    val manufacturer: String,
    val costInCredits: String
)

data class PlanetData(
    val name: String,
    val climate: String,
    val terrain: String,
    val population: String
)

data class SpeciesData(
    val name: String,
    val classification: String,
    val designation: String,
    val averageLifeSpan: String,
)

data class MovieData(
    val title: String,
    val episode: Int?,
    val director: String,
    val releaseDate: String,
    val overview: String
)

fun Character.toCharacterDetails(
    planet: Planet?, movie: List<Movie>, species: List<Specie>
) = CharacterDetails(
    name = name,
    gender = formatNullableString(gender),
    height = height,
    birthYear = birthYear,
    hairColor = formatNullableString(hairColor),
    eyeColor = formatNullableString(eyeColor),
    planetData = planet?.toPlanetData(),
    speciesData = species.map { it.toSpecieData() },
    featuredEpisodes = movie.map { it.toMovieData() }
)

fun Movie.toMovieData() = MovieData(
    title = title,
    episode = episodeId,
    director = director,
    releaseDate = releaseDate,
    overview = openingCrawl
)

fun Planet.toPlanetData() = PlanetData(
    name = formatNullableString(name),
    climate = formatNullableString(climate),
    terrain = formatNullableString(terrain),
    population = formatNullableString(population)
)


private fun Specie.toSpecieData() = SpeciesData(
    name = formatNullableString(name),
    classification = formatNullableString(classification),
    designation = formatNullableString(designation),
    averageLifeSpan = formatNullableString(averageLifespan),
)

internal fun formatNullableString(name: String?) = name ?: "N/A"