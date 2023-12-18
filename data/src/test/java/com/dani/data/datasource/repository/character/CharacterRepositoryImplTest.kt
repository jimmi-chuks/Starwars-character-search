package com.dani.data.datasource.repository.character

import com.dani.data.datasource.repository.CoroutineTest
import com.dani.data.fakes.FakeCharacterDatasource
import com.dani.shared_test.fakes.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test

//import org.junit.Assert.*

@ExperimentalCoroutinesApi
class CharacterRepositoryImplTest: CoroutineTest() {

    @Test
    fun getCharacterDetails_success() = runCoroutineTest {
        val characterDataSource = FakeCharacterDatasource
        val repository = CharacterRepositoryImpl(characterDataSource, testDispatcher)

        // When character is fetched
        val characterResult = repository.getCharacter("test")

        // Assert values
        assertEquals(characterResult, TestData.character1)
    }


    @Test
    fun searchCharacter_success() = runCoroutineTest {
        // Given a datasource which returns success
        val searchResult = TestData.searchResult1
        val characterDataSource = FakeCharacterDatasource
        val repository = CharacterRepositoryImpl(characterDataSource, testDispatcher)

        // When search is performed
        val characterResult = repository.searchCharacter("test")

        // Assert values
        assertEquals(characterResult, TestData.searchResult1)
    }

    @Test
    fun searchCharacterByPage_success() = runCoroutineTest {
        // Given a datasource which returns success
        val characterDataSource = FakeCharacterDatasource
        val repository = CharacterRepositoryImpl(characterDataSource, testDispatcher)

        // When search is performed
        val characterResult = repository.searchCharacterByPage(1, "test")

        // Assert values
        assertEquals(characterResult, TestData.searchResult2)
    }

}