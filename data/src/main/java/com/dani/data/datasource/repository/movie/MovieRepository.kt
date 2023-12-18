package com.dani.data.datasource.repository.movie

import com.dani.model.dto.Movie

interface MovieRepository{
    suspend fun getMovieDetails(id:String): Movie
}
