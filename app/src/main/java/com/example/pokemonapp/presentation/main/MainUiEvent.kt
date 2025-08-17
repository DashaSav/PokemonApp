package com.example.pokemonapp.presentation.main

import com.example.pokemonapp.domain.model.Pokemon

sealed interface MainUiEvent {

    data class OnItemClicked(val item: Pokemon) : MainUiEvent

    data class OnSearchChanged(val search: String) : MainUiEvent

    data object OnSearchClicked : MainUiEvent

    data object OnFiltersClicked : MainUiEvent

    data object OnDismissBottomSheet: MainUiEvent

    data object OnApplyFiltersClicked : MainUiEvent

    data object OnNextPageLoad : MainUiEvent

    data object OnRefresh : MainUiEvent

    data class OnSortSelect(val index: Int) : MainUiEvent

    data class OnTypeSelect(val type: TypeFilter) : MainUiEvent
}
