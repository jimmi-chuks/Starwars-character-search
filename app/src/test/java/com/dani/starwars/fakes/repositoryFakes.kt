package com.dani.starwars.fakes

import com.dani.data.datasource.local.entities.RecentSearchEntity
import com.dani.data.datasource.local.recentsearches.RecentSearchLocalDataSource
import com.dani.data.datasource.remote.character.RemoteCharacterDataSource
import com.dani.data.datasource.remote.movie.RemoteMovieDataSource
import com.dani.data.datasource.remote.planet.RemotePlanetDataSource
import com.dani.data.datasource.remote.specie.RemoteSpecieDataSource
import com.dani.data.datasource.remote.starship.RemoteStarshipDataSource
import com.dani.data.datasource.remote.vehicle.RemoteVehicleDataSource
import com.dani.model.dto.*
import com.dani.shared_test.fakes.TestData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import java.io.IOException

object Shared {
    val resultException = IOException("Error")
}

class FakeCharacterDatasource(
    private val characterList: MutableList<Character>? = null,
    private val searchResult: SearchResult? = null
) : RemoteCharacterDataSource {

    override suspend fun getCharacterDetails(characterId: String): Character {
        return if (characterList.isNullOrEmpty()) {
            throw Shared.resultException
        } else {
            characterList[0]
        }
    }

    override suspend fun searchCharacter(searchParam: String): SearchResult {
        return searchResult ?: throw Shared.resultException
    }

    override suspend fun searchCharactersByPage(
        page: Int,
        searchParam: String
    ): SearchResult {
        return searchResult ?: throw Shared.resultException
    }
}

class FakeMovieDataSource(private val movie: Movie) : RemoteMovieDataSource {
    override suspend fun getMovieDetails(id: String): Movie = movie
}

class FakeRemotePlanetDatasource(private val planet: Planet) : RemotePlanetDataSource {
    override suspend fun searchPlanet(searchParam: String): Planet = planet
}

class FakeStarshipRemoteDatasource(private val starship: Starship) : RemoteStarshipDataSource {

    override suspend fun getStarship(id: String): Starship = starship
}

class FakeRemoteVehicleDataSource(private val vehicle: Vehicle) : RemoteVehicleDataSource {
    override suspend fun getVehicle(id: String): Vehicle = vehicle
}

object FakeSpecieRemoteDataSource : RemoteSpecieDataSource {
    override suspend fun getSpecieDetails(id: String): Specie = TestData.specie1

}

class FakeRecentSearchLocalDataSource(private val recentSearches: MutableList<RecentSearchEntity>) :
    RecentSearchLocalDataSource {


    private val flow: MutableSharedFlow<List<RecentSearchEntity>> = MutableSharedFlow<List<RecentSearchEntity>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    ). apply { tryEmit(recentSearches) }

    override fun getAll(): Flow<List<RecentSearchEntity>> = flow

    override suspend fun getMatchingQuery(searchParam: String): List<RecentSearchEntity> {
        return flow.first().filter { it.searchString.contains(searchParam) }
    }

    override suspend fun deleteAll() {
        flow.tryEmit(emptyList())
    }

    override suspend fun insert(searchParam: String) {
        flow.tryEmit(flow.first() + RecentSearchEntity(searchParam, 1L))
    }

}