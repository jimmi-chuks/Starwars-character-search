package com.dani.data.fakes


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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

object Shared {
    val resultException = IOException("Error")
}

object FakeCharacterDatasource : RemoteCharacterDataSource {

    override suspend fun getCharacterDetails(characterId: String): Character =
        TestData.character1

     override suspend fun searchCharacter(searchParam: String): SearchResult = TestData.searchResult1

    override suspend fun searchCharactersByPage(page: Int, searchParam: String): SearchResult =
        TestData.searchResult2

}

object FakeMovieDataSource: RemoteMovieDataSource {

    override suspend fun getMovieDetails(id: String): Movie = TestData.movie1
}

object FakeRemotePlanetDatasource : RemotePlanetDataSource {

    override suspend fun searchPlanet(searchParam: String): Planet = TestData.planet1

}

object FakeStarshipRemoteDatasource : RemoteStarshipDataSource {

    override suspend fun getStarship(id: String): Starship = TestData.starship1

}

object FakeRemoteVehicleDataSource : RemoteVehicleDataSource {

    override suspend fun getVehicle(id: String): Vehicle = TestData.vehicle

}

object FakeSpecieRemoteDataSource : RemoteSpecieDataSource {
    override suspend fun getSpecieDetails(id: String): Specie = TestData.specie1

}

class FakeRecentSearchLocalDataSource(private val recentSearches: MutableList<RecentSearchEntity>): RecentSearchLocalDataSource{

    override  fun getAll(): Flow<List<RecentSearchEntity>> = flow {  recentSearches }

    override suspend fun getMatchingQuery(searchParam: String): List<RecentSearchEntity> {
        return recentSearches.filter { it.searchString.contains(searchParam) }
    }

    override suspend fun deleteAll() {
        recentSearches.clear()
    }

    override suspend fun insert(searchParam: String) {
        val searchEntity = RecentSearchEntity(searchParam, 1L)
        recentSearches.add(searchEntity)
    }

}
