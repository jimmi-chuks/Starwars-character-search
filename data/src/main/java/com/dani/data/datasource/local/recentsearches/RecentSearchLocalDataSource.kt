package com.dani.data.datasource.local.recentsearches

import com.dani.data.datasource.local.daos.RecentSearchDao
import com.dani.data.datasource.local.entities.RecentSearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RecentSearchLocalDataSource {
    fun getAll(): Flow<List<RecentSearchEntity>>

    suspend fun getMatchingQuery(searchParam: String): List<RecentSearchEntity>

    suspend fun deleteAll()

    suspend fun insert(searchParam: String)
}

class RecentSearchLocalDataSourceImpl @Inject constructor(private val dao: RecentSearchDao) :
    RecentSearchLocalDataSource {

    override fun getAll(): Flow<List<RecentSearchEntity>> = dao.getAll()

    override suspend fun getMatchingQuery(searchParam: String): List<RecentSearchEntity> {
        return dao.getMatchingQuery(searchParam) ?: emptyList()
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun insert(searchParam: String) {
        val currentTime = System.currentTimeMillis()
        val recentSearchEntity = (RecentSearchEntity(searchParam, currentTime))
        dao.insert(recentSearchEntity)
    }
}