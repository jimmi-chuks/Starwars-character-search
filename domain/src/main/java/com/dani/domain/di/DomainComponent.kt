package com.dani.domain.di

import android.content.Context
import com.dani.data.DispatchersProvider
import com.dani.data.datasource.module.DatabaseModule
import com.dani.data.datasource.repository.character.CharacterRepository
import com.dani.data.datasource.repository.movie.MovieRepository
import com.dani.data.datasource.repository.planet.PlanetRepository
import com.dani.data.datasource.repository.recentsearch.RecentSearchRepository
import com.dani.data.datasource.repository.specie.SpecieRepository
import com.dani.data.datasource.repository.starship.StarshipRepository
import com.dani.data.datasource.repository.vehicle.VehicleRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DomainModule::class, DatabaseModule::class])
@Singleton
interface DomainComponent {

    val characterRepository: CharacterRepository
    val movieRepository: MovieRepository
    val planetRepository: PlanetRepository
    val specieRepository: SpecieRepository
    val starshipRepository: StarshipRepository
    val vehicleRepository: VehicleRepository
    val recentSearchRepository: RecentSearchRepository
    val providesDispatchers: DispatchersProvider

    @Component.Builder
    interface Builder {
        fun build(): DomainComponent
        @BindsInstance
        fun applicationContext(context: Context): Builder
    }

}
