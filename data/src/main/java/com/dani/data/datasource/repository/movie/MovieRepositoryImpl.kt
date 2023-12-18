package com.dani.data.datasource.repository.movie

import com.dani.data.datasource.remote.movie.RemoteMovieDataSource
import com.dani.model.dto.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {

    override suspend fun getMovieDetails(id: String): Movie =
        withContext(ioDispatcher) { remoteMovieDataSource.getMovieDetails(id) }
}

