package com.dani.data.datasource.repository.starship

import com.dani.data.datasource.repository.CoroutineTest
import com.dani.data.fakes.FakeStarshipRemoteDatasource
import com.dani.data.fakes.Shared
import com.dani.shared_test.fakes.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class StarshipRepositoryImplTest : CoroutineTest(){

    @ExperimentalCoroutinesApi
    @Test
    fun getStarship_success() = runBlockingTest {
        // Given a repository with a datasource that returns values successfully
        val starshipDataSource = FakeStarshipRemoteDatasource
        val repository: StarshipRepository = StarshipRepositoryImpl(starshipDataSource, testDispatcher)

        // When get starship is performed
        val result = repository.getStarship("testId")

        // Assert values
        assertEquals(result, TestData.starship1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getStarship_failure() = runBlockingTest {
        // Given a repository with a datasource that returns error
        val starshipDataSource = FakeStarshipRemoteDatasource
        val repository: StarshipRepository = StarshipRepositoryImpl(starshipDataSource, testDispatcher)

        // When get starship is performed
        val result = repository.getStarship("testId")

        // Assert values
        assertEquals(result, Shared.resultException)
    }
}