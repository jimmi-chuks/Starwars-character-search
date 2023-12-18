package com.dani.data.datasource.remote.movie

import com.dani.model.dto.Movie

interface RemoteMovieDataSource {
    suspend fun getMovieDetails(id: String): Movie
}