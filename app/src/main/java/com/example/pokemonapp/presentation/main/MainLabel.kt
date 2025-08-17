package com.example.pokemonapp.presentation.main

sealed interface MainLabel{
    data object OnNavigateToPokemonDetail : MainLabel
}
