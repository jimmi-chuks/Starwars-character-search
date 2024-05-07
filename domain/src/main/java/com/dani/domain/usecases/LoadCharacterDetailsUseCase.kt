package com.dani.domain.usecases

import arrow.core.Either
import arrow.fx.coroutines.parZip
import com.dani.data.DispatchersProvider
import com.dani.data.datasource.repository.movie.MovieRepository
import com.dani.data.datasource.repository.planet.PlanetRepository
import com.dani.data.datasource.repository.specie.SpecieRepository
import com.dani.data.extensions.parallelFetchList
import com.dani.data.getLastPath
import com.dani.model.dto.Character
import com.dani.model.dto.Movie
import com.dani.model.dto.Specie
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class LoadCharacterDetailsUseCase @Inject constructor(
    private val planetRepository: PlanetRepository,
    private val specieRepository: SpecieRepository,
    private val movieRepository: MovieRepository,
    private val dispatchersProvider: DispatchersProvider
) {

    suspend fun getFullCharacterDetails(character: Character): Either<Throwable, CharacterDetails> =
        coroutineScope {
            Either.catch {
                val specieIdList = character.species?.mapNotNull { getLastPath(it) }
                val movieUrlList = character.films.mapNotNull { getLastPath(it) }
                val planetId: String? = character.homeWorld?.let { getLastPath(it) }
                parZip(
                    { specieIdList?.let { getSpeciesDetails(it) } ?: emptyList() },
                    { planetId?.let { planetRepository.searchPlanet(it) } },
                    { getMoviesDetails(movieUrlList) },
                ) { species, planet, movie ->
                    character.toCharacterDetails(planet, movie, species)
                }
            }
        }

    private suspend fun getMoviesDetails(movieList: List<String>): List<Movie> =
        coroutineScope {
            movieList.parallelFetchList(
                coroutineContext = dispatchersProvider.io,
                block = { movieRepository.getMovieDetails(it) }
            )
        }

    private suspend fun getSpeciesDetails(specieList: List<String>): List<Specie> = coroutineScope {
        specieList.parallelFetchList(dispatchersProvider.io) {
            specieRepository.getSpecieDetails(it)
        }
    }
}