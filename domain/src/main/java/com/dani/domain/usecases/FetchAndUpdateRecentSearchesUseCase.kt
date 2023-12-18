package com.dani.domain.usecases

import arrow.core.Either
import com.dani.data.datasource.local.entities.RecentSearchEntity
import com.dani.data.datasource.repository.recentsearch.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAndUpdateRecentSearchesUseCase @Inject constructor(private val recentSearchRepository: RecentSearchRepository) {

    suspend fun addToRecentSearch(query: String) {
        recentSearchRepository.insertSearchFromQuery(query)
    }

    suspend fun getMatchingQuery(query: String): Either<Throwable, List<RecentSearchEntity>> =
        Either.catch { recentSearchRepository.getMatchingQuery(query) }

    suspend fun clearRecentSearches() {
        recentSearchRepository.clearRecentSearched()
    }

     fun getRecentSearches(): Flow<List<RecentSearchEntity>> =
        recentSearchRepository.getAll()
}