package com.example.pokemonapp.data

import com.example.pokemonapp.data.mappers.PokemonMapper
import com.example.pokemonapp.data.mappers.toDomain
import com.example.pokemonapp.domain.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface PokemonRepository {
    suspend fun getPokemons(offset: Int, limit: Int): List<Pokemon>
    suspend fun searchPokemon(query: String): Pokemon?
}

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val pokemonLocalDataSource: PokemonLocalDataSource,
    private val networkChecker: NetworkChecker,
    private val pokemonMapper: PokemonMapper
) : PokemonRepository {
    override suspend fun getPokemons(
        offset: Int,
        limit: Int,
    ): List<Pokemon> = withContext(Dispatchers.IO) {
        if (networkChecker.isInternetAvailable()) {
            val pokemons = pokemonRemoteDataSource.getAllPokemons(offset, limit)
            pokemonLocalDataSource.insertPokemons(pokemons)
            pokemonMapper.map(pokemons)
        } else {
            pokemonMapper.mapEntities(pokemonLocalDataSource.getAllPokemons(offset, limit))
        }
    }

    override suspend fun searchPokemon(query: String): Pokemon? {
        return if (networkChecker.isInternetAvailable()) {
            pokemonRemoteDataSource.searchPokemon(query)?.toDomain()
        } else {
            pokemonLocalDataSource.searchPokemon(query)?.toDomain()
        }
    }
}
