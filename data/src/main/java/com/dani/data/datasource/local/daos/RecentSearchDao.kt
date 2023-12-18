package com.dani.data.datasource.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dani.data.datasource.local.entities.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {

    @Query("SELECT * FROM recent_searches ORDER BY time DESC LIMIT 10")
    fun getAll(): Flow<List<RecentSearchEntity>>

    @Query("SELECT * FROM recent_searches WHERE search_string LIKE '%' || :query || '%' LIMIT 10")
    suspend fun getMatchingQuery(query: String): List<RecentSearchEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RecentSearchEntity)

    @Query("DELETE FROM recent_searches")
    fun deleteAll()
}