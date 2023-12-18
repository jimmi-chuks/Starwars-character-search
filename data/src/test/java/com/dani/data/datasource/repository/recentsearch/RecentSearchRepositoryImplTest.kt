package com.dani.data.datasource.repository.recentsearch

import com.dani.data.datasource.local.entities.RecentSearchEntity
import com.dani.data.datasource.local.recentsearches.RecentSearchLocalDataSource
import com.dani.data.datasource.repository.CoroutineTest
import com.dani.data.fakes.FakeRecentSearchLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class RecentSearchRepositoryImplTest : CoroutineTest() {

    val searchEntry1 = RecentSearchEntity("first-here", 1L)
    val searchEntry2 = RecentSearchEntity("second-here", 2L)
    val testSearchEntries: MutableList<RecentSearchEntity> = mutableListOf(searchEntry1)

    @Test
    fun getAllRecentSearches() = runBlockingTest{
        // Given
        val dataSource: RecentSearchLocalDataSource = FakeRecentSearchLocalDataSource(testSearchEntries)
        val repo = RecentSearchRepositoryImpl(dataSource, testDispatcher)

        // When get all searches is requested
        val all = repo.getAll()

        // Assert values
        assertEquals(all, testSearchEntries)
    }

    @Test
    fun getMatchingQuery_success() = runBlockingTest{
        // Given
        val allSearches = mutableListOf(searchEntry1, searchEntry2)
        val dataSource: RecentSearchLocalDataSource = FakeRecentSearchLocalDataSource(allSearches)
        val repo = RecentSearchRepositoryImpl(dataSource, testDispatcher)

        // When search for different params
        val returnAll = repo.getMatchingQuery("here")
        val notFound = repo.getMatchingQuery("random")

        // Assert values
        assertEquals(returnAll, allSearches)
        assertEquals(notFound, emptyList<RecentSearchEntity>())
    }


    @Test
    fun clearRecentSearched() = runBlockingTest{
        // Given
        val dataSource: RecentSearchLocalDataSource = FakeRecentSearchLocalDataSource(testSearchEntries)
        val repo = RecentSearchRepositoryImpl(dataSource, testDispatcher)

        // Verify size before clearing entries
        val beforeClearing = repo.getAll().first().size
        assertEquals(1, beforeClearing)

        // When recent searches are cleared
        repo.clearRecentSearched()

        val afterClearing = repo.getAll().first().size

        // Verify difference after clearing
        assertEquals(0, afterClearing)
    }


    @Test
    fun insertSearchFromQuery() = runBlockingTest {
        // Given a repo with only one recent searh
        val dataSource: RecentSearchLocalDataSource = FakeRecentSearchLocalDataSource(testSearchEntries)
        val repo = RecentSearchRepositoryImpl(dataSource, testDispatcher)

        // Verify size before clearing entries
        val beforeInserting = repo.getAll().first().size
        assertEquals(1, beforeInserting)

        // When a new entry is inserted
        repo.insertSearchFromQuery("another")

        // verify increase in saved recents
        val afterInserting = repo.getAll().first().size
        assertEquals(2, afterInserting)
    }

}