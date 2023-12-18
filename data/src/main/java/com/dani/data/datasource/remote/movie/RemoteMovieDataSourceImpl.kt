package com.dani.data.datasource.remote.movie

import com.dani.data.datasource.remote.StarWarsApiService
import com.dani.model.dto.Movie
import javax.inject.Inject

class RemoteMovieDataSourceImpl @Inject constructor(val starWarsApiService: StarWarsApiService) :
    RemoteMovieDataSource {

    override suspend fun getMovieDetails(id: String): Movie =
        starWarsApiService.getMovieDetails(id)

}