package com.dani.starwars.screens.home


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.flowWithLifecycle
import com.dani.compose_ui.GoogleComposeFont
import com.dani.compose_ui.StarWarsTheme
import com.dani.compose_ui.StarwarsColors
import com.dani.starwars.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToSearchScreen: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    HomeScreenContent(
        suggestions = if (state.searchParam.isBlank()) state.recentSearches else state.searchContainingQuery,
        fullScreenSearchActive = state.searchActive,
        onBackPressed = { viewModel.onEvent(HomeViewModel.Event.BackNavigation) },
        performSearch = { viewModel.onEvent(HomeViewModel.Event.PerformCharacterSearch(it)) },
        onSearchQueryUpdated = { viewModel.onEvent(HomeViewModel.Event.SearchUpdated(it)) },
        clearRecentSearches = { viewModel.onEvent(HomeViewModel.Event.ClearRecentSearches) },
        onSearchActive = { viewModel.onEvent(HomeViewModel.Event.SearchActiveChange(it)) },
    )

    val currentLifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(viewModel) {
        viewModel.effect.flowWithLifecycle(currentLifecycle).collect { effect ->
            when (effect) {
                HomeViewModel.Effect.NavigateBack -> onNavigateBack()
                is HomeViewModel.Effect.NavigateToSearchScreen -> onNavigateToSearchScreen(effect.searchParam)
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    suggestions: List<String> = emptyList(),
    fullScreenSearchActive: Boolean = false,
    onBackPressed: () -> Unit,
    performSearch: (String) -> Unit,
    clearRecentSearches: () -> Unit,
    onSearchActive: (Boolean) -> Unit,
    onSearchQueryUpdated: (searchString: String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (!fullScreenSearchActive) {
            TopSection()
        }

        val editableTextInputState = rememberEditableInputState(placeholder = "Search")
        val currentOnSearchQueryUpdated by rememberUpdatedState(onSearchQueryUpdated)

        LaunchedEffect(editableTextInputState) {
            snapshotFlow { editableTextInputState.text }
                .collect { currentOnSearchQueryUpdated(editableTextInputState.text) }
        }

        SearchComposable(
            textInputState = editableTextInputState,
            onSearchActive = onSearchActive,
            searchActive = fullScreenSearchActive,
            performSearch = { performSearch(editableTextInputState.text) },
            searchContent = {
                RecentSearchSuggestions(
                    recentSearches = suggestions,
                    searchSuggestion = performSearch,
                    clearRecentSearches = clearRecentSearches
                )
            }
        )

        BackHandler(enabled = true) {
            editableTextInputState.clear()
            onBackPressed()
        }
    }
}

@Composable
internal fun TopSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("Top Section"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(id = R.string.app_label),
            color = StarwarsColors.Purple200,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                fontFamily = FontFamily(GoogleComposeFont.bangers),
                fontSize = 8.em
            )
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(id = R.string.character_search_intro),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(id = R.string.character_search_tutorial),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(72.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchComposable(
    textInputState: EditableTextInputState,
    onSearchActive: (Boolean) -> Unit = {},
    searchActive: Boolean = false,
    performSearch: () -> Unit = {},
    searchContent: @Composable () -> Unit,
) {

    Box(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = -1f },
            query = textInputState.text,
            onQueryChange = { textInputState.updateText(it) },
            onSearch = { performSearch() },
            active = searchActive,
            onActiveChange = onSearchActive,
            placeholder = {
                Text(
                    text = textInputState.placeholder,
                    fontWeight = FontWeight.Light
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.clickable { performSearch() }
                )
            },
            trailingIcon = {
                if (searchActive) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.clickable { textInputState.clear() }
                    )
                }
            },
        ) {
            searchContent()
        }
    }
}

@Composable
private fun RecentSearchSuggestions(
    recentSearches: List<String>,
    searchSuggestion: (String) -> Unit,
    clearRecentSearches: () -> Unit
) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        val recentSearchesEmpty = recentSearches.isEmpty()
        if (!recentSearchesEmpty) {
            item {
                Text(text = "recent Searches")
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
        items(recentSearches) { searchItem ->
            ListItem(
                headlineContent = { Text(searchItem) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { searchSuggestion(searchItem) }
            )
        }
        if (!recentSearchesEmpty) {
            item {
                Text(
                    text = stringResource(id = R.string.clear_recent_searches),
                    modifier = Modifier.clickable { clearRecentSearches() }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

class EditableTextInputState(initialText: String, val placeholder: String = "") {

    var text by mutableStateOf(initialText)
        private set

    fun updateText(newText: String) {
        text = newText
    }

    fun clear() {
        text = ""
    }

    companion object {
        private const val TEXT_KEY = "text"
        val Saver: Saver<EditableTextInputState, *> = mapSaver(
            save = { mapOf(TEXT_KEY to it.text) },
            restore = {
                EditableTextInputState(initialText = it[TEXT_KEY]?.toString() ?: "")
            },
        )
    }
}

@Composable
fun rememberEditableInputState(
    initialText: String = "",
    placeholder: String = ""
): EditableTextInputState = rememberSaveable(initialText, saver = EditableTextInputState.Saver) {
    EditableTextInputState(initialText, placeholder)
}

@Preview
@Composable
fun HomeScreen_Landing() {
    StarWarsTheme {
        Surface {
            HomeScreenContent(
                suggestions = emptyList(),
                fullScreenSearchActive = false,
                onBackPressed = {},
                performSearch = {},
                onSearchQueryUpdated = {},
                clearRecentSearches = {},
                onSearchActive = {}
            )
        }
    }
}

@Preview
@Composable
fun HomeScreen_Search() {
    StarWarsTheme {
        Surface {
            HomeScreenContent(
                suggestions = listOf("o", "Paul", "Loki", "Tor", "a"),
                fullScreenSearchActive = true,
                onBackPressed = {},
                performSearch = {},
                onSearchQueryUpdated = {},
                clearRecentSearches = {},
                onSearchActive = {}
            )
        }
    }
}