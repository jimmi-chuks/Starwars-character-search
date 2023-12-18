package com.dani.data.datasource.repository.recentsearch

import com.dani.data.datasource.local.entities.RecentSearchEntity
import com.dani.data.datasource.local.recentsearches.RecentSearchLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecentSearchRepositoryImpl @Inject constructor(
    private val recentSearchLocalDataSource: RecentSearchLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RecentSearchRepository {

    override fun getAll(): Flow<List<RecentSearchEntity>> =
        recentSearchLocalDataSource.getAll()

    override suspend fun getMatchingQuery(searchParam: String): List<RecentSearchEntity> =
        withContext(ioDispatcher) { recentSearchLocalDataSource.getMatchingQuery(searchParam) }

    override suspend fun clearRecentSearched() =
        withContext(ioDispatcher) { recentSearchLocalDataSource.deleteAll() }

    override suspend fun insertSearchFromQuery(searchParam: String) =
        withContext(ioDispatcher) { recentSearchLocalDataSource.insert(searchParam) }
}