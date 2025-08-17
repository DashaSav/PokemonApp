package com.example.pokemonapp.domain.usecase

import com.example.pokemonapp.domain.model.Pokemon
import javax.inject.Inject

class FilterPokemonUseCase @Inject constructor() {
    fun filterPokemon(filters: List<String>, pokemons: List<Pokemon>): List<Pokemon> {
        return pokemons.filter { pokemon ->
           filters.any { filter ->
               pokemon.types.any { type ->
                   filter.lowercase().contains(type.type.lowercase())
               }
           }
        }
    }
}