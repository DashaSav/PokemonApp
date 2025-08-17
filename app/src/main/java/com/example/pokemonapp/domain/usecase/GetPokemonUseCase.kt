package com.example.pokemonapp.domain.usecase

import com.example.pokemonapp.data.PokemonRepository
import com.example.pokemonapp.domain.model.Pokemon
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) {
    suspend fun getPokemons(offset: Int, limit: Int): List<Pokemon> {
        return pokemonRepository.getPokemons(offset, limit)
    }
}