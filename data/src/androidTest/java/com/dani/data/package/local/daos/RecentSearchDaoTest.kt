package com.dani.data.`package`.local.daos

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dani.data.datasource.local.AppDatabase
import com.dani.data.datasource.local.daos.RecentSearchDao
import com.dani.data.datasource.local.entities.RecentSearchEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RecentSearchDaoTest {


    @get:Rule
    var instantExecRule = InstantTaskExecutorRule()
    lateinit var recentSearchDao: RecentSearchDao
    lateinit var db: AppDatabase

    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun createDb() {
        Dispatchers.setMain(testDispatcher)
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        recentSearchDao = db.recentSearchDao()
    }

    @After
    @Throws(*[IOException::class])
    fun closeDb(){
        db.close()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    @Throws(*[Exception::class])
    fun insertAndReadInList() = runBlockingTest(TestCoroutineDispatcher()){
        val first =  RecentSearchEntity("dave", 23199292L)
        recentSearchDao.insert(first)
        val addedSearch = recentSearchDao.getAll().firstOrNull()!![0]
        assertThat(addedSearch, `is`(first))
    }

    @Test
    @Throws(*[Exception::class])
    fun insertAndSearchmatchingQuery() = runBlockingTest{
        val first =  RecentSearchEntity("dave", 23199292L)
        val second =  RecentSearchEntity("plane", 2319929L)
        val third =  RecentSearchEntity("ane", 2319929L)
        recentSearchDao.apply {
            insert(first)
            insert(second)
            insert(third)
        }
        val addedSearches = recentSearchDao.getMatchingQuery("an")!!
        assertThat(addedSearches.size, `is`(2))
        assertFalse(addedSearches.contains(first))
        assertTrue(addedSearches.contains(second))
        assertTrue(addedSearches.contains(third))
    }


    @Test
    @Throws(*[Exception::class])
    fun deleteAll() = runBlockingTest{
        val first =  RecentSearchEntity("dave", 23199292L)
        val second =  RecentSearchEntity("plane", 2319929L)
        recentSearchDao.apply {
            insert(first)
            insert(second)
        }
        val addedSearches = recentSearchDao.getAll().firstOrNull()!!
        assertThat(addedSearches.size, `is`(2))

        recentSearchDao.deleteAll()

        val addedSearchesAfterDeleting = recentSearchDao.getAll().firstOrNull()!!
        assertThat(addedSearchesAfterDeleting.size, `is`(0))
    }

}