package com.dani.domain.usecases

import arrow.core.Either
import com.dani.data.datasource.repository.character.CharacterRepository
import com.dani.model.dto.SearchResult
import javax.inject.Inject

class SearchCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend fun searchCharacters(searchParam: String): Either<Throwable, SearchResult> =
        Either.catch { characterRepository.searchCharacter(searchParam) }

    suspend fun getNextCharacterPage(page: Int, query: String): Either<Throwable, SearchResult> =
        Either.catch{ characterRepository.searchCharacterByPage(page, query) }

}