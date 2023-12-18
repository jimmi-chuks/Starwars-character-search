package com.dani.data.datasource.repository.planet

import com.dani.data.datasource.repository.CoroutineTest
import com.dani.data.fakes.FakeRemotePlanetDatasource
import com.dani.shared_test.fakes.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class PlanetRepositoryImplTest: CoroutineTest() {

    @Test
    fun searchPlanet() = runBlockingTest{
        // Given a repository with a datasource that returns values successfully
        val remoteMovieDataSource = FakeRemotePlanetDatasource
        val repository: PlanetRepository = PlanetRepositoryImpl(remoteMovieDataSource, testDispatcher)

        // When get planet search is performed
        val result = repository.searchPlanet("testId")

        // Assert values
        assertEquals(result, TestData.planet1)
    }
}