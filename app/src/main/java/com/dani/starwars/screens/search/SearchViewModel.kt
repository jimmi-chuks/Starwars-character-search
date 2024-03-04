package com.dani.starwars.screens.search

import android.net.UrlQuerySanitizer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dani.data.DispatchersProvider
import com.dani.data.getLastPath
import com.dani.domain.usecases.SearchCharacterUseCase
import com.dani.model.dto.Character
import com.dani.starwars.navigation.DestinationArgs
import com.dani.starwars.screens.home.HomeViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


@Serializable
class SearchArgs(val searchParam: String) : DestinationArgs

class SearchViewModel(
    private val searchCharacterUseCase: SearchCharacterUseCase,
    private val args: SearchArgs,
    val dispatchersProvider: DispatchersProvider,
) : ViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect = _effect.asSharedFlow()

    init {
        searchCharacters()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.BackButtonTapped -> onBackButtonTapped()
            is Event.CharacterTapped -> onCharacterTapped(event.characterUrl)
            Event.RetrySearch -> retry()
            Event.LastItemReached -> onLastItemReached()
        }
    }

    private fun onLastItemReached() {
        val currentState = _state.value
        val nextPageUrl = currentState.nextPageUrl
        val displayState = currentState.displayState
        if (!nextPageUrl.isNullOrBlank() && displayState != DisplayState.NEXT_PAGE_LOADING) {
            _state.value = _state.value.copy(displayState = DisplayState.NEXT_PAGE_LOADING)

            val (page, query) = UrlQuerySanitizer(nextPageUrl).run {
                Pair(getValue("page").toInt(), getValue("search"))
            }

            viewModelScope.launch(dispatchersProvider.io) {
                searchCharacterUseCase.getNextCharacterPage(page, query).fold(
                    ifRight = { searchResult ->
                        val state = _state.value
                        _state.value = state.copy(
                            displayState = DisplayState.NO_LOADING,
                            characters = (state.characters + searchResult.results).distinct(),
                            nextPageUrl = searchResult.next
                        )
                    },
                    ifLeft =  {
                        val state = _state.value
                        _state.value = state.copy(displayState = DisplayState.NO_LOADING)
                        _effect.tryEmit(Effect.ShowNextPageError)
                    }
                )
            }
        }
    }

    private fun onBackButtonTapped() {
        _effect.tryEmit(Effect.NavigateBack)
    }

    private fun onCharacterTapped(characterUrl: String) {
        val characterId = requireNotNull(getLastPath(characterUrl) )
        _effect.tryEmit(Effect.NavigateToCharacterDetails(characterId))
    }

    private fun retry() {
        _state.value = _state.value.copy(displayState = DisplayState.INITIAL_LOADING)
        searchCharacters()
    }

    private fun searchCharacters() {
        viewModelScope.launch(dispatchersProvider.io) {
            _state.value =  searchCharacterUseCase.searchCharacters(args.searchParam).fold(
                ifLeft = {
                    Log.e(HomeViewModel.TAG, "search Characters error: $it")
                    _state.value.copy(displayState = DisplayState.ERROR)
                },
                ifRight = {searchResult ->
                    _state.value.copy(
                        nextPageUrl = searchResult.next,
                        characters = searchResult.results,
                        displayState = DisplayState.NO_LOADING,
                    )
                }
            )
        }
    }

    sealed class Event {
        data object RetrySearch : Event()
        data object BackButtonTapped : Event()
        data object LastItemReached : Event()
        data class CharacterTapped(val characterUrl: String) : Event()
    }

    sealed class Effect {
        data class NavigateToCharacterDetails(val characterId: String) : Effect()
        data object NavigateBack : Effect()
        data object ShowNextPageError : Effect()
    }

    data class State(
        val nextPageUrl: String? = null,
        val characters: List<Character> = emptyList(),
        val displayState: DisplayState = DisplayState.INITIAL_LOADING
    )

    enum class DisplayState {
        INITIAL_LOADING, NO_LOADING, NEXT_PAGE_LOADING, ERROR
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}