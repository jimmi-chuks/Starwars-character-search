package com.dani.starwars

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dani.SpaceXApplicationComponent
import com.dani.data.DispatchersProvider
import com.dani.domain.usecases.FetchAndUpdateRecentSearchesUseCase
import com.dani.domain.usecases.LoadCharacterDetailsUseCase
import com.dani.domain.usecases.SearchCharacterUseCase
import com.dani.starwars.di.DaggerHomeComponent
import com.dani.starwars.di.HomeComponent
import com.dani.starwars.screens.home.HomeScreen
import com.dani.starwars.screens.home.HomeViewModel
import com.dani.starwars.navigation.ArgumentDestination
import com.dani.starwars.navigation.createRouteWithArgs
import com.dani.starwars.navigation.retrieveParams
import com.dani.starwars.screens.character.details.CharacterDetailsArgs
import com.dani.starwars.screens.character.details.CharacterDetailsScreen
import com.dani.starwars.screens.character.details.CharacterDetailsViewModel
import com.dani.starwars.screens.character.search.CharacterFetcher
import com.dani.starwars.screens.character.search.SearchArgs
import com.dani.starwars.screens.character.search.SearchListScreen
import com.dani.starwars.screens.character.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class MainActivity : AppCompatActivity() {

    lateinit var homeComponent: HomeComponent

    @Inject
    lateinit var searchCharacterUseCase: SearchCharacterUseCase

    @Inject
    lateinit var dispatchersProvider: DispatchersProvider

    @Inject
    lateinit var recentSearchesUseCase: FetchAndUpdateRecentSearchesUseCase

    @Inject
    lateinit var loadCharacterDetailsUseCase: LoadCharacterDetailsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        buildDaggerComponent()
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    private fun buildDaggerComponent() {
        homeComponent = DaggerHomeComponent.builder()
            .domainComponent(
                SpaceXApplicationComponent.domainComponentFromContext(this)
            )
            .bindActivity(this)
            .build()
        homeComponent.inject(this)
    }

    @Composable
    fun Content() {
        val navController = rememberNavController()

        navController.addOnDestinationChangedListener { _, dest, bundle ->
            Log.d(TAG, "on destination changed")
            dest.run {
                Log.d(TAG, "navDestination parent: ${dest.parent}")
                Log.d(TAG, "navDestination route: ${dest.route}")
                Log.d(TAG, "navDestination arguments: ${dest.arguments}")
            }
            Log.d(TAG, "bundle: $bundle")

        }

        NavHost(navController = navController, startDestination = Destination.HomeRoute.ROUTE) {
            composable(route = Destination.HomeRoute.ROUTE) {
                val viewModel = viewModel { HomeViewModel(recentSearchesUseCase) }
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        if (!navController.popBackStack()) {
                            onBackPressedDispatcher.onBackPressed()
                        }
                    },
                    onNavigateToSearchScreen = {
                        val route =
                            Destination.CharacterSearchList.createRouteWithArgs(SearchArgs(it))
                        navController.navigate(route.value)
                    }
                )
            }

            characterSearchNavigation(navController = navController)
        }
    }

    private fun NavGraphBuilder.characterSearchNavigation(
        startDestination: String = Destination.SearchRoute.ROUTE,
        route: String = Destination.CharacterSearchList.getRoute().value,
        navController: NavHostController
    ) {
        navigation(route = route, startDestination = startDestination) {
            composable(route = Destination.SearchRoute.ROUTE) { backStackEntry ->
                val viewModel: SearchViewModel = getSearchViewModel(backStackEntry, navController)
                SearchListScreen(
                    viewModel = viewModel, navigateBack = { navController.popBackStack() },
                    navigateToCharacter = {
                        val characterDetailsArgs = CharacterDetailsArgs(it)
                        navController.navigate(
                            Destination.CharacterDetails.createRouteWithArgs(characterDetailsArgs).value
                        )
                    }
                )
            }

            composable(route = Destination.CharacterDetails.getRoute().value) { backStackEntry ->
                val characterDetailsArg =
                    Destination.CharacterDetails.retrieveParams(backStackEntry.arguments)
                val characterFetcher = getCharacterFetcher(backStackEntry, navController)
                val viewModel: CharacterDetailsViewModel = viewModel(
                    initializer = {
                        CharacterDetailsViewModel(
                            loadCharacterDetailsUseCase = loadCharacterDetailsUseCase,
                            character = characterFetcher.getCharacter(characterDetailsArg.characterUrl),
                            dispatchersProvider = dispatchersProvider,
                        )
                    }
                )

                CharacterDetailsScreen(
                    viewModel = viewModel,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }

    @Composable
    private fun getSearchViewModel(
        navBackStackEntry: NavBackStackEntry,
        navController: NavHostController
    ): SearchViewModel {
        val parentEntry: NavBackStackEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(Destination.CharacterSearchList.getRoute().value)
        }

        val searchArg = Destination.CharacterSearchList.retrieveParams(parentEntry.arguments)
        return viewModel(
            viewModelStoreOwner = parentEntry,
            key = searchArg.searchParam,
            initializer = {
                SearchViewModel(
                    searchCharacterUseCase = searchCharacterUseCase,
                    dispatchersProvider = dispatchersProvider,
                    args = searchArg
                )
            }
        )
    }

    @Composable
    private fun getCharacterFetcher(
        navBackStackEntry: NavBackStackEntry,
        navController: NavHostController
    ): CharacterFetcher = getSearchViewModel(navBackStackEntry, navController)

    object Destination {
        data object HomeRoute {
            const val ROUTE: String = "HomeRoute"
        }

        data object SearchRoute {
            const val ROUTE: String = "SearchRoute"
        }

        data object CharacterSearchList : ArgumentDestination<SearchArgs>
        data object CharacterDetails : ArgumentDestination<CharacterDetailsArgs>
    }

    companion object {
        const val TAG = "MainActivity Log!"
    }
}
