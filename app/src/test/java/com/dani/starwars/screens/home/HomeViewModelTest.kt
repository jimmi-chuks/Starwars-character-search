package com.dani.starwars.screens.home

import app.cash.turbine.test
import com.dani.data.datasource.repository.recentsearch.RecentSearchRepositoryImpl
import com.dani.domain.usecases.FetchAndUpdateRecentSearchesUseCase
import com.dani.starwars.MainDispatcherRule
import com.dani.starwars.fakes.AppModuleTestData
import com.dani.starwars.fakes.FakeRecentSearchLocalDataSource
import com.dani.starwars.screens.home.HomeViewModel
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private val dataSource = FakeRecentSearchLocalDataSource(AppModuleTestData.searches)

    private val useCase =
        FetchAndUpdateRecentSearchesUseCase(RecentSearchRepositoryImpl(dataSource, testDispatcher))

    private val searchResult = AppModuleTestData.searches.map { it.searchString }

    val stateWithSearchResult = HomeViewModel.State(recentSearches = searchResult)

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(useCase, testDispatcher)
    }

    @Test
    fun `Verify initial state update`() = runTest {
        viewModel.state.test {
            assertEquals(HomeViewModel.State(recentSearches = searchResult), awaitItem())
        }
    }

    @Test
    fun `Verify state update when ClearRecentSearches event is fired`() = runTest {
        viewModel.state.test {
            assertEquals(stateWithSearchResult, awaitItem())

            viewModel.onEvent(HomeViewModel.Event.ClearRecentSearches)

            assertEquals(HomeViewModel.State(recentSearches = emptyList()), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `Verify state update when SearchActiveChange event is fired`() = runTest {
        viewModel.state.test {
            assertEquals(stateWithSearchResult, awaitItem())
            viewModel.onEvent(HomeViewModel.Event.SearchActiveChange(true))
            assertEquals(stateWithSearchResult.copy(searchActive = true), awaitItem())
            viewModel.onEvent(HomeViewModel.Event.SearchActiveChange(false))
            assertEquals(stateWithSearchResult.copy(searchActive = false), awaitItem())
        }
    }

    @Test
    fun `Verify state update when search is not active and BackNavigation event is fired`() = runTest {

        viewModel.setState(stateWithSearchResult)

        viewModel.effect.test {

            // Fire back navigation when search is active
            viewModel.onEvent(HomeViewModel.Event.BackNavigation)

            // Verify state changes
            assertEquals(
                HomeViewModel.Effect.NavigateBack,
                awaitItem()
            )

        }
    }

    @Test
    fun `Verify state update when search is active and BackNavigation event is fired`() = runTest {
        val initialState =
            stateWithSearchResult.copy(searchActive = true, searchParam = "test param")
        viewModel.setState(initialState)

        viewModel.state.test {
            // Verify initial state
            assertEquals(initialState, awaitItem())

            // Fire back navigation when search is active
            viewModel.onEvent(HomeViewModel.Event.BackNavigation)

            // Verify state changes
            assertEquals(
                initialState.copy(searchActive = false, searchContainingQuery = emptyList()),
                awaitItem()
            )

        }
    }
    @Test
    fun `Verify state update when PerformCharacterSearch event is fired`() = runTest {
        val searchParam = "Search Param"

        viewModel.effect.test {

            // Fire back navigation when search is active
            viewModel.onEvent(HomeViewModel.Event.PerformCharacterSearch(searchParam))

            // Verify state changes
            assertEquals(
                HomeViewModel.Effect.NavigateToSearchScreen(searchParam),
                awaitItem()
            )

        }
    }


}