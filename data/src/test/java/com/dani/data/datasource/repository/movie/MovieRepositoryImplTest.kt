package com.dani.data.datasource.repository.movie


import com.dani.data.datasource.repository.CoroutineTest
import com.dani.data.fakes.FakeMovieDataSource
import com.dani.shared_test.fakes.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest : CoroutineTest() {

    @Test
    fun getMovieDetails_success() = runBlockingTest {
        // Given a repository with a datasource that returns values successfully
        val remoteMovieDataSource = FakeMovieDataSource
        val repository: MovieRepository = MovieRepositoryImpl(remoteMovieDataSource, testDispatcher)

        // When get movie details is performed
        val movie = repository.getMovieDetails("testId")

        // Assert values
        assertEquals(movie, TestData.movie1)
    }
}