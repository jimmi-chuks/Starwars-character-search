package com.dani.data.datasource.repository

import com.dani.data.datasource.repository.character.CharacterRepository
import com.dani.data.datasource.repository.character.CharacterRepositoryImpl
import com.dani.data.datasource.repository.movie.MovieRepository
import com.dani.data.datasource.repository.movie.MovieRepositoryImpl
import com.dani.data.datasource.repository.planet.PlanetRepository
import com.dani.data.datasource.repository.planet.PlanetRepositoryImpl
import com.dani.data.datasource.repository.recentsearch.RecentSearchRepository
import com.dani.data.datasource.repository.recentsearch.RecentSearchRepositoryImpl
import com.dani.data.datasource.repository.specie.SpecieRepository
import com.dani.data.datasource.repository.specie.SpecieRepositoryImpl
import com.dani.data.datasource.repository.starship.StarshipRepository
import com.dani.data.datasource.repository.starship.StarshipRepositoryImpl
import com.dani.data.datasource.repository.vehicle.VehicleRepository
import com.dani.data.datasource.repository.vehicle.VehicleRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindCharacterRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository

    @Binds
    abstract fun bindMoviesRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindPlanetsRepository(planetRepositoryImpl: PlanetRepositoryImpl): PlanetRepository

    @Binds
    abstract fun bindSpecieRepository(specieRepositoryImpl: SpecieRepositoryImpl): SpecieRepository

    @Binds
    abstract fun bindStarshipRepository(starshipRepositoryImpl: StarshipRepositoryImpl): StarshipRepository

    @Binds
    abstract fun bindVehiclesRepository(vehicleRepositoryImpl: VehicleRepositoryImpl): VehicleRepository

    @Binds
    abstract fun bindRecentSearchesRepository(recentSearchRepositoryImpl: RecentSearchRepositoryImpl): RecentSearchRepository
}