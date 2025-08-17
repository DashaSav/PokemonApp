package com.example.pokemonapp.data.mappers

import com.example.pokemonapp.data.db.PokemonEntity
import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.domain.model.Stat
import com.example.pokemonapp.domain.model.Type
import javax.inject.Inject
import com.example.pokemonapp.data.model.Pokemon as PokemonData

class PokemonMapper @Inject constructor() {
    fun mapEntities(pokemons: List<PokemonEntity>): List<Pokemon> {
        return pokemons.map { it.toDomain() }
    }

    fun map(pokemons: List<PokemonData>): List<Pokemon> {
        return pokemons.map { it.toDomain() }
    }
}

fun PokemonData.toDomain() = Pokemon(
    id = this.id,
    name = this.name,
    imageUrl = this.sprites.frontDefault,
    types = this.types.map { type ->
        Type(type = type.name.name)
    },
    stats = this.stats.map { stat ->
        Stat(
            baseStat = stat.baseStat,
            name = stat.name.name
        )
    },
)

fun PokemonEntity.toDomain() = Pokemon(
    id = this.id,
    name = this.name,
    imageUrl = this.imageUrl.orEmpty(),
    types = this.types.map { type ->
        Type(type = type.name)
    },
    stats = this.stats.map { stat ->
        Stat(
            baseStat = stat.baseStat,
            name = stat.name
        )
    },
)