package com.example.pokemonapp.data

import androidx.core.net.toUri
import com.example.pokemonapp.data.model.Pokemon
import javax.inject.Inject

interface PokemonRemoteDataSource {
    suspend fun getAllPokemons(offset: Int, limit: Int): List<Pokemon>
    suspend fun getPokemon(id: Int): Pokemon
    suspend fun searchPokemon(query: String): Pokemon?
}

class PokemonRemoteDataSourceImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
): PokemonRemoteDataSource {
    override suspend fun getAllPokemons(offset: Int, limit: Int): List<Pokemon> {
        val result = pokemonApi.getAllPokemons(offset, limit)

        return result.results.mapNotNull { pokemonBase ->
            val id = pokemonBase.url.toUri().lastPathSegment?.toIntOrNull()
            id?.let { getPokemon(it) }
        }
    }

    override suspend fun getPokemon(id: Int): Pokemon {
       return pokemonApi.getPokemon(id)
    }

    override suspend fun searchPokemon(query: String): Pokemon? {
        return try { pokemonApi.getPokemon(query) }
        catch (_: Exception) {
            return null
        }
    }
}
