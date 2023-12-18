package com.dani.data.datasource.remote.character

import com.dani.model.dto.Character
import com.dani.model.dto.SearchResult

interface RemoteCharacterDataSource {
    suspend fun getCharacterDetails(characterId: String): Character

    suspend fun searchCharacter(searchParam: String): SearchResult

    suspend fun searchCharactersByPage(page: Int, searchParam: String): SearchResult
}