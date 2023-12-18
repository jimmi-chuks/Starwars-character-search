package com.dani.starwars.screens.home

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dani.domain.usecases.FetchAndUpdateRecentSearchesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val recentSearchesUseCase: FetchAndUpdateRecentSearchesUseCase,
    backgroundDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()

    init {
        recentSearchesUseCase.getRecentSearches()
            .flowOn(backgroundDispatcher)
            .onEach { list ->
                val searches = list.map { it.searchString }
                _state.value = _state.value.copy(recentSearches = searches)
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.ClearRecentSearches -> onClearRecentSearches()
            is Event.PerformCharacterSearch -> onPerformCharacterSearch(event.searchParam)
            is Event.SearchUpdated -> onSearchUpdated(event.searchQuery)
            Event.BackNavigation -> onBackNavigation()
            is Event.SearchActiveChange -> onSearchActiveChange(event.isActive)
        }
    }

    private fun onSearchActiveChange(isActive: Boolean) {
        _state.value = _state.value.copy(searchActive = isActive)
    }

    private fun onBackNavigation() {
        val currentState = _state.value
        if (currentState.searchActive) {
            _state.value =
                _state.value.copy(searchContainingQuery = emptyList(), searchActive = false)
        } else {
            viewModelScope.launch {
                _effect.emit(Effect.NavigateBack)
            }
        }
    }

    private fun onSearchUpdated(searchQuery: String) {
        viewModelScope.launch {
            val searchSuggestions = if (searchQuery.isBlank()) {
                emptyList()
            } else {
                recentSearchesUseCase.getMatchingQuery(searchQuery).fold(
                    ifLeft = {
                        Log.e(TAG, "onSearchUpdated error: $it")
                        emptyList()
                    },
                    ifRight = { it.map { it.searchString } }
                )
            }
            _state.value = _state.value.copy(
                searchParam = searchQuery,
                searchContainingQuery = searchSuggestions
            )
        }
    }

    private fun onPerformCharacterSearch(searchParam: String) {
        if (searchParam.isNotBlank()) {
            viewModelScope.launch {
                recentSearchesUseCase.addToRecentSearch(searchParam)
                _effect.emit(Effect.NavigateToSearchScreen(searchParam))
            }
        }
    }

    private fun onClearRecentSearches() {
        viewModelScope.launch {
            recentSearchesUseCase.clearRecentSearches()
        }
        _state.value = _state.value.copy(searchContainingQuery = emptyList())
    }

    @VisibleForTesting
    fun setState(state: State){
        _state.value = state
    }

    sealed class Effect {
        data object NavigateBack : Effect()
        data class NavigateToSearchScreen(val searchParam: String) : Effect()
    }

    sealed class Event {
        data class SearchUpdated(val searchQuery: String) : Event()
        data object ClearRecentSearches : Event()
        data class PerformCharacterSearch(val searchParam: String) : Event()
        data object BackNavigation : Event()
        data class SearchActiveChange(val isActive: Boolean) : Event()
    }

    data class State(
        val searchParam: String = "",
        val recentSearches: List<String> = emptyList(),
        val searchContainingQuery: List<String> = emptyList(),
        val searchActive: Boolean = false,
    )

    companion object {
        const val TAG = "HomeViewModel"
    }
}