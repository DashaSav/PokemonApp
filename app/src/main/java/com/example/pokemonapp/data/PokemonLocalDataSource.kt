package com.example.pokemonapp.data

import com.example.pokemonapp.data.db.PokemonEntity
import com.example.pokemonapp.data.db.StatDB
import com.example.pokemonapp.data.db.TypeDB
import com.example.pokemonapp.data.db.dao.PokemonDao
import com.example.pokemonapp.data.model.Pokemon
import javax.inject.Inject

interface PokemonLocalDataSource {
    suspend fun getAllPokemons(offset: Int, limit: Int): List<PokemonEntity>
    suspend fun insertPokemons(pokemons: List<Pokemon>)
    suspend fun searchPokemon(query: String): PokemonEntity?
}

class PokemonLocalDataSourceImpl @Inject constructor(
    private val dao: PokemonDao
) : PokemonLocalDataSource {
    override suspend fun getAllPokemons(offset: Int, limit: Int): List<PokemonEntity> {
        return dao.fetchPokemons(limit, offset)
    }

    override suspend fun insertPokemons(pokemons: List<Pokemon>) {
        dao.insert(pokemons.map {
            PokemonEntity(
                id = it.id,
                name = it.name,
                imageUrl = it.sprites.frontDefault,
                types = it.types.map { type ->
                    TypeDB(name = type.name.name)
                },
                stats = it.stats.map { stat ->
                    StatDB(
                        baseStat = stat.baseStat,
                        name = stat.name.name
                    )
                }
            )
        })
    }

    override suspend fun searchPokemon(query: String): PokemonEntity? {
        return dao.getPokemon(query)
    }
}
