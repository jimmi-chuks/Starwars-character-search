package com.dani.data.datasource.repository.recentsearch

import com.dani.data.datasource.local.entities.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {

    fun getAll(): Flow<List<RecentSearchEntity>>

    suspend fun insertSearchFromQuery(searchParam: String)

    suspend fun getMatchingQuery(searchParam: String): List<RecentSearchEntity>

    suspend fun clearRecentSearched()
}