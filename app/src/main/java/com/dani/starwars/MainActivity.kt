package com.dani.starwars

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import com.dani.starwars.screens.characterdetails.CharacterDetailsArgs
import com.dani.starwars.screens.characterdetails.CharacterDetailsScreen
import com.dani.starwars.screens.characterdetails.CharacterDetailsViewModel
import com.dani.starwars.screens.search.SearchArgs
import com.dani.starwars.screens.search.SearchListScreen
import com.dani.starwars.screens.search.SearchViewModel
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

        NavHost(navController = navController, startDestination = Destination.HomeRoute.route) {
            composable(route = Destination.HomeRoute.route) {
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

            composable(route = Destination.CharacterSearchList.getRoute().value) {
                val searchArg = Destination.CharacterSearchList.retrieveParams(it.arguments)
                val viewModel: SearchViewModel = viewModel(
                    key = searchArg.searchParam,
                    initializer = {
                        SearchViewModel(
                            searchCharacterUseCase = searchCharacterUseCase,
                            dispatchersProvider = dispatchersProvider,
                            args = searchArg
                        )
                    }
                )

                SearchListScreen(
                    viewModel = viewModel,
                    navigateBack = {
                        navController.popBackStack()
                    },
                    navigateToCharacter = {
                        val route =
                            Destination.CharacterDetails.createRouteWithArgs(CharacterDetailsArgs(it))
                        navController.navigate(route.value)
                    }
                )
            }

            composable(route = Destination.CharacterDetails.getRoute().value) {
                val characterDetailsArg = Destination.CharacterDetails.retrieveParams(it.arguments)

                val viewModel: CharacterDetailsViewModel = viewModel(
                    initializer = {
                        CharacterDetailsViewModel(
                            loadCharacterDetailsUseCase = loadCharacterDetailsUseCase,
                            dispatchersProvider = dispatchersProvider,
                            detailsArgs = characterDetailsArg
                        )
                    }
                )

                CharacterDetailsScreen(
                    viewModel = viewModel,
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }

    object Destination {
        data object HomeRoute {
            val route: String = "HomeRoute"
        }

        data object CharacterSearchList : ArgumentDestination<SearchArgs>
        data object CharacterDetails : ArgumentDestination<CharacterDetailsArgs>
    }
}
