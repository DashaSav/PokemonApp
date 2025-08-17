package com.example.pokemonapp.domain.usecase

import com.example.pokemonapp.data.PokemonRepository
import com.example.pokemonapp.domain.model.Pokemon
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
){
    suspend fun search(query: String): Pokemon? {
        return repository.searchPokemon(query)
    }
}