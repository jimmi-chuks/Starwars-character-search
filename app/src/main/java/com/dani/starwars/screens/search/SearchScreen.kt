package com.dani.starwars.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import com.dani.compose_ui.StarWarsTheme
import com.dani.starwars.R
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchListScreen(
    viewModel: SearchViewModel,
    navigateBack: () -> Unit,
    navigateToCharacter: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val currentLifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(viewModel) {
        viewModel.effect.flowWithLifecycle(lifecycle = currentLifecycle).collect {
            when (it) {
                SearchViewModel.Effect.NavigateBack -> navigateBack()
                is SearchViewModel.Effect.NavigateToCharacterDetails -> navigateToCharacter(it.characterId)
                SearchViewModel.Effect.ShowNextPageError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("Unable to fetch next page")
                    }
                }
            }
        }
    }

    StarWarsTheme {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Character Search") },
                    navigationIcon = {
                        IconButton(
                            onClick = { viewModel.onEvent(SearchViewModel.Event.BackButtonTapped) },
                            content = { Icon(Icons.Filled.ArrowBack, "Back Button") }
                        )
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { padding ->
            Content(
                modifier = Modifier.padding(padding),
                state = state,
                onCharacterTapped = { viewModel.onEvent(SearchViewModel.Event.CharacterTapped(it)) },
                onRetry = { viewModel.onEvent(SearchViewModel.Event.RetrySearch) },
                onLastIndexReached = { viewModel.onEvent(SearchViewModel.Event.LastItemReached) }
            )
        }
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    state: SearchViewModel.State,
    onCharacterTapped: (String) -> Unit,
    onRetry: () -> Unit,
    onLastIndexReached: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        if (state.displayState == SearchViewModel.DisplayState.INITIAL_LOADING) {
            BoxCircularProgress()
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                if (state.displayState == SearchViewModel.DisplayState.ERROR) {
                    Error(onRetry = onRetry)
                } else {
                    val listState = rememberLazyListState()

                    LaunchedEffect(listState) {
                        snapshotFlow { listState.canScrollForward }
                            .distinctUntilChanged()
                            .filter { !it }
                            .collect { onLastIndexReached() }
                    }

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        state = listState
                    ) {
                        items(state.characters) { character ->
                            CharacterRow(
                                name = character.name,
                                birthYear = character.birthYear,
                                gender = character.gender ?: "NA",
                                onCharacterTapped = { onCharacterTapped(character.url) }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                    if (state.characters.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "No Matching Character")
                        }
                    }
                    if (state.displayState == SearchViewModel.DisplayState.NEXT_PAGE_LOADING) {
                        BoxCircularProgress(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        )
                    }
                }
            }
        }
    }


}

@Composable
internal fun Error(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TextButton(
            onClick = onRetry,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
        ) {
            Text("Retry")
        }
    }
}


@Composable
fun CharacterRow(
    name: String,
    birthYear: String,
    gender: String,
    onCharacterTapped: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onCharacterTapped() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_face_24),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(text = name)
                LabelRow(text = birthYear, label = stringResource(id = R.string.birth_year))
                LabelRow(text = gender, label = stringResource(id = R.string.gender))
            }
        }
    }
}

@Composable
private fun LabelRow(text: String, label: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, modifier = Modifier.padding(end = 4.dp))
        Text(text = text)
    }
}

@Composable
private fun BoxCircularProgress(modifier: Modifier = Modifier.fillMaxSize()) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun Character_preview() {
    StarWarsTheme {
        Surface {
            CharacterRow(name = "Charles Okafor", birthYear = "19972", gender = "male") {

            }
        }
    }
}