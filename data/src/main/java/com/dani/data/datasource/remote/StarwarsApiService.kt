package com.dani.data.datasource.remote

import com.dani.model.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApiService {

    @GET("/api/people/")
    suspend fun searchCharacters(@Query("search") searchParam: String): SearchResult

    @GET("/api/people/")
    suspend fun searchCharactersByPage(
        @Query("page") page: Int,
        @Query("search") searchParam: String
    ): SearchResult

    @GET("/api/people/{id}/")
    suspend fun getCharacterDetails(@Path("id") characterId: String): Character

    @GET("/api/films/{id}/")
    suspend fun getMovieDetails(@Path("id") id: String): Movie

    @GET("/api/starships/{id}/")
    suspend fun getStarshipDetails(@Path("id") id: String): Starship

    @GET("/api/vehicles/{id}/")
    suspend fun getVehicleDetails(@Path("id") id: String): Vehicle

    @GET("/api/species/{id}/")
    suspend fun getSpecieDetails(@Path("id") id: String): Specie

    @GET("/api/planets/{id}/")
    suspend fun getPlanetDetails(@Path("id") id: String): Planet
}