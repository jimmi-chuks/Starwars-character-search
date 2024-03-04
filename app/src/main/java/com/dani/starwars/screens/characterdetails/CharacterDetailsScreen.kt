package com.dani.starwars.screens.characterdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import com.dani.compose_ui.StarWarsTheme
import com.dani.domain.usecases.CharacterDetails
import com.dani.domain.usecases.MovieData
import com.dani.domain.usecases.PlanetData
import com.dani.domain.usecases.SpeciesData
import com.dani.starwars.R


@Composable
fun CharacterDetailsScreen(
    viewModel: CharacterDetailsViewModel,
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Content(
        characterDetails = state.characterDetails,
        loading = state.loading,
        showError = state.showError,
        onBackButtonTapped = { viewModel.onEvent(CharacterDetailsViewModel.Event.NavigateBack) },
        onRetryDataFetch = { viewModel.onEvent(CharacterDetailsViewModel.Event.RetryDataFetch) }
    )


    val currentLifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(viewModel) {
        viewModel.effect.flowWithLifecycle(currentLifecycle).collect { effect ->
            when(effect){
                CharacterDetailsViewModel.Effect.NavigateBack -> {
                    navigateBack()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Content(
    characterDetails: CharacterDetails?,
    loading: Boolean,
    showError: Boolean,
    onBackButtonTapped: () -> Unit,
    onRetryDataFetch: () -> Unit
) {
    StarWarsTheme {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.title_character_details)) },
                    navigationIcon = {
                        IconButton(
                            onClick = { onBackButtonTapped() },
                            content = { Icon(Icons.Filled.ArrowBack, "Back Button") }
                        )
                    },
                    scrollBehavior = scrollBehavior
                )
            },
        ) { padding ->
            if (loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (characterDetails == null || showError) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(modifier = Modifier
                        .clickable { onRetryDataFetch() }
                        .padding(16.dp)) {
                        Text(
                            text = stringResource(id = R.string.error_loading_character),
                            modifier = Modifier.padding(8.dp)
                        )
                        TextButton(onClick = { onRetryDataFetch() }) {
                            Text(stringResource(id = R.string.refresh))
                        }
                    }
                }
            } else {
                CharacterDetailsContent(
                    characterDetails = characterDetails,
                    paddingValues = padding
                )
            }
        }
    }
}

@Composable
internal fun CharacterDetailsContent(
    characterDetails: CharacterDetails,
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        item {
            BioSection(
                name = characterDetails.name,
                birthYear = characterDetails.birthYear,
                height = characterDetails.height,
                hairColour = characterDetails.hairColor,
                eyeColour = characterDetails.eyeColor,
                gender = characterDetails.gender
            )
        }

        if(characterDetails.speciesData.isNotEmpty()){
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(characterDetails.speciesData){ specieItem ->
                SpecieItem(speciesData = specieItem)
            }
        }

        characterDetails.planetData?.let { planetData ->
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HomeWorldSection(planetData = planetData)
            }
        }

        if (characterDetails.featuredEpisodes.isNotEmpty()) {
            item {
                Text(
                    text = "Featured Episodes",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(characterDetails.featuredEpisodes) { movieData ->
                MovieItem(movieData = movieData)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
internal fun BioSection(
    name: String,
    birthYear: String,
    height: String,
    hairColour: String,
    eyeColour: String,
    gender: String
) {
    Card(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            ItemLabelRow(label = "Name", value = name)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Birth Year", value = birthYear)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Gender", value = gender)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Height", value = height)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Hair Colour", value = hairColour)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Eye Colour", value = eyeColour)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
internal fun HomeWorldSection(planetData: PlanetData) {
    Card(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Home World",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Name", value = planetData.name)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Climate", value = planetData.climate)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Terrain", value = planetData.terrain)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Population", value = planetData.population)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
internal fun SpecieItem(speciesData: SpeciesData) {
    Card(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            ItemLabelRow(label = "Name", value = speciesData.name)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Classification", value = speciesData.classification)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Designation", value = speciesData.designation)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Average LifeSpan", value = speciesData.averageLifeSpan)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
internal fun MovieItem(movieData: MovieData) {
    Card(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            ItemLabelRow(label = "Title", value = movieData.title)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Episode", value = movieData.episode?.toString() ?: "N/A")
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Director", value = movieData.director)
            Spacer(modifier = Modifier.height(8.dp))

            ItemLabelRow(label = "Release Date", value = movieData.releaseDate)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Overview")
            Text(text = movieData.overview)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun ItemLabelRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = value, textAlign = TextAlign.End)
    }
}