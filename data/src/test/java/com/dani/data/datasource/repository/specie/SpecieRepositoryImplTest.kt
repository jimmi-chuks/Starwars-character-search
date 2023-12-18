package com.dani.data.datasource.repository.specie

import com.dani.data.datasource.repository.CoroutineTest
import com.dani.data.fakes.FakeSpecieRemoteDataSource
import com.dani.shared_test.fakes.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class SpecieRepositoryImplTest : CoroutineTest() {

    @Test
    fun getMovieDetails_success() = runBlockingTest {
        // Given a repository with a datasource that returns values successfully
        val specieDataSource = FakeSpecieRemoteDataSource
        val repository: SpecieRepository = SpecieRepositoryImpl(specieDataSource, testDispatcher)

        // When get movie details is performed
        val result = repository.getSpecieDetails("testId")

        // Assert values
        assertEquals(result, TestData.specie1)
    }

}