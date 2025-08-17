package com.example.pokemonapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.domain.model.SortType
import com.example.pokemonapp.domain.usecase.FilterPokemonUseCase
import com.example.pokemonapp.domain.usecase.GetPokemonUseCase
import com.example.pokemonapp.domain.usecase.SearchPokemonUseCase
import com.example.pokemonapp.domain.usecase.SortPokemonUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val typeFiltersDefaults = listOf(
    TypeFilter("Normal"),
    TypeFilter("Fire"),
    TypeFilter("Water"),
    TypeFilter("Electric"),
    TypeFilter("Grass"),
    TypeFilter("Ice"),
    TypeFilter("Fighting"),
    TypeFilter("Poison"),
    TypeFilter("Ground"),
    TypeFilter("Flying"),
    TypeFilter("Psychic"),
    TypeFilter("Bug"),
    TypeFilter("Rock"),
    TypeFilter("Ghost"),
    TypeFilter("Dragon"),
    TypeFilter("Dark"),
    TypeFilter("Steel"),
    TypeFilter("Fairy"),
)

data class MainUiState(
    val search: String = "",
    val pokemons: List<Pokemon> = emptyList(),
    val chosenPokemon: Pokemon? = null,
    val showBottomSheet: Boolean = false,
    val sortOptions: List<String> = listOf("ID", "NAME", "HP", "ATTACK", "DEF"),
    val selectedSortIndex: Int = 0,
    val typeFilters: List<TypeFilter> = typeFiltersDefaults,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val offset: Int = 0,
    val limit: Int = 20,
)

data class TypeFilter(val name: String, val selected: Boolean = false)

class MainViewModel @Inject constructor(
    private val getPokemonUseCase: GetPokemonUseCase,
    private val filterPokemonUseCase: FilterPokemonUseCase,
    private val sortPokemonUseCase: SortPokemonUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    val labels = Channel<MainLabel>()

    init {
        viewModelScope.launch { loadInitialData() }
    }

    fun onEvent(event: MainUiEvent) {
        viewModelScope.launch {
            when (event) {
                is MainUiEvent.OnItemClicked ->
                    labels.send(MainLabel.OnNavigateToPokemonDetail)

                is MainUiEvent.OnSearchChanged -> onSearchChanged(event.search)
                MainUiEvent.OnFiltersClicked -> onFiltersClicked()
                MainUiEvent.OnDismissBottomSheet -> onDismissBottomSheet()
                is MainUiEvent.OnSortSelect -> onSortSelect(event.index)
                is MainUiEvent.OnTypeSelect -> onTypeSelect(event.type)
                MainUiEvent.OnApplyFiltersClicked -> applyFilters()
                MainUiEvent.OnSearchClicked -> onSearchClicked()
                MainUiEvent.OnNextPageLoad -> onNextPage()
                MainUiEvent.OnRefresh -> onRefresh()
            }
        }
    }

    private fun onNextPage() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val limit = _uiState.value.limit
            val newOffset = _uiState.value.offset + limit
            val oldPokemons = _uiState.value.pokemons

            val newPokemons = oldPokemons + getPokemonUseCase.getPokemons(newOffset, limit)

            _uiState.update { it.copy(isLoading = false, pokemons = newPokemons, offset = newOffset) }
        }
    }

    private fun onRefresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }

            val limit = _uiState.value.limit

            val newPokemons = getPokemonUseCase.getPokemons(0, limit)

            _uiState.update { it.copy(isRefreshing = false, pokemons = newPokemons, offset = 0) }
        }
    }

    private fun onSearchClicked() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            if (uiState.value.search.isNotEmpty()) {
                val foundPokemon = searchPokemonUseCase.search(uiState.value.search)
                _uiState.update {
                    it.copy(pokemons = listOfNotNull(foundPokemon), isLoading = false)
                }
            } else {
                loadInitialData()
            }
        }
    }

    private fun onSearchChanged(text: String) {
        _uiState.update { it.copy(search = text) }
    }

    private fun onFiltersClicked() {
        _uiState.update { it.copy(showBottomSheet = true) }
    }

    private fun onDismissBottomSheet() {
        _uiState.update { it.copy(showBottomSheet = false) }
    }

    private fun onSortSelect(index: Int) {
        _uiState.update { it.copy(selectedSortIndex = index) }
    }

    private fun onTypeSelect(type: TypeFilter) {
        _uiState.update {
            val updatedTypes = _uiState.value.typeFilters.map { oldType ->
                if (type == oldType) TypeFilter(type.name, !type.selected)
                else oldType
            }

            it.copy(typeFilters = updatedTypes)
        }
    }

    private fun applyFilters() {
        viewModelScope.launch {
            val index = _uiState.value.selectedSortIndex
            val selectedSort = SortType.valueOf(_uiState.value.sortOptions[index])

            val selectedFilters = uiState.value.typeFilters
                .filter { it.selected }
                .map { it.name }

            val filtered = if (selectedFilters.isNotEmpty()) {
                filterPokemonUseCase.filterPokemon(selectedFilters, uiState.value.pokemons)
            } else {
                uiState.value.pokemons
            }

            val sorted = sortPokemonUseCase.sortPokemons(filtered, selectedSort)

            _uiState.update { it.copy(pokemons = sorted) }
        }
    }

    private suspend fun loadInitialData() {
        _uiState.update { it.copy(isLoading = true) }
        val pokemons = getPokemonUseCase.getPokemons(0, 20)
        _uiState.update { it.copy(pokemons = pokemons, isLoading = false) }
    }
}
