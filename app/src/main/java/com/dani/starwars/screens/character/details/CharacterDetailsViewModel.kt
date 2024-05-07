package com.dani.starwars.screens.character.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dani.data.DispatchersProvider
import com.dani.domain.usecases.CharacterDetails
import com.dani.domain.usecases.LoadCharacterDetailsUseCase
import com.dani.model.dto.Character
import com.dani.starwars.navigation.DestinationArgs
import com.dani.starwars.screens.character.search.CharacterFetcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class CharacterDetailsArgs(val characterUrl: String) : DestinationArgs

class CharacterDetailsViewModel @Inject constructor(
    private val loadCharacterDetailsUseCase: LoadCharacterDetailsUseCase,
    val dispatchersProvider: DispatchersProvider,
    private val character: Character,
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect = _effect.asSharedFlow()

    init {
        fetchCharacterDetails()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.NavigateBack -> onNavigateBack()
            Event.RetryDataFetch -> fetchCharacterDetails()
        }
    }

    private fun onNavigateBack() {
        viewModelScope.launch { _effect.emit(Effect.NavigateBack) }
    }

    private fun fetchCharacterDetails() {
        viewModelScope.launch(dispatchersProvider.io) {
            _state.value = _state.value.copy(loading = true)
            loadCharacterDetailsUseCase.getFullCharacterDetails(character)
                .onRight {
                    _state.value = _state.value.copy(
                        characterDetails = it,
                        loading = false,
                        showError = false
                    )
                }.onLeft {
                    _state.value = _state.value.copy(
                        characterDetails = null,
                        loading = false,
                        showError = true
                    )
                }
        }
    }

    data class State(
        val characterDetails: CharacterDetails? = null,
        val loading: Boolean = true,
        val showError: Boolean = false
    )

    sealed class Event {
        data object NavigateBack : Event()
        data object RetryDataFetch : Event()
    }

    sealed class Effect {
        data object NavigateBack : Effect()
    }

}