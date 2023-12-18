package com.dani.data.datasource.repository.character


import com.dani.model.dto.Character
import com.dani.model.dto.SearchResult

interface CharacterRepository {
    suspend fun getCharacter(characterId: String): Character

    suspend fun searchCharacter(searchParam: String): SearchResult

    suspend fun searchCharacterByPage(page: Int, searchParam: String): SearchResult
}
