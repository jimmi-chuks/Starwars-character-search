package com.dani.data.datasource.repository.character

import com.dani.data.datasource.remote.character.RemoteCharacterDataSource
import com.dani.model.dto.Character
import com.dani.model.dto.SearchResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteCharacterDataSource: RemoteCharacterDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : CharacterRepository {

    override suspend fun getCharacter(characterId: String): Character =
        withContext(ioDispatcher) {
            remoteCharacterDataSource.getCharacterDetails(characterId)
        }


    override suspend fun searchCharacter(searchParam: String): SearchResult =
        withContext(ioDispatcher) {
            remoteCharacterDataSource.searchCharacter(searchParam)
        }

    override suspend fun searchCharacterByPage(
        page: Int,
        searchParam: String
    ): SearchResult =
        withContext(ioDispatcher) {
            remoteCharacterDataSource.searchCharactersByPage(page, searchParam)
        }
}
