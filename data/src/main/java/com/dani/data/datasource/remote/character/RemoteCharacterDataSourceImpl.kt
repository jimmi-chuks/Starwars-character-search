package com.dani.data.datasource.remote.character

import com.dani.data.datasource.remote.StarWarsApiService
import com.dani.model.dto.Character
import com.dani.model.dto.SearchResult
import javax.inject.Inject

class RemoteCharacterDataSourceImpl @Inject constructor(val apiService: StarWarsApiService) :
    RemoteCharacterDataSource {

    override suspend fun getCharacterDetails(characterId: String): Character =
        apiService.getCharacterDetails(characterId)

    override suspend fun searchCharacter(searchParam: String): SearchResult =
        apiService.searchCharacters(searchParam)

    override suspend fun searchCharactersByPage(
        page: Int,
        searchParam: String
    ): SearchResult = apiService.searchCharactersByPage(page, searchParam)

}
