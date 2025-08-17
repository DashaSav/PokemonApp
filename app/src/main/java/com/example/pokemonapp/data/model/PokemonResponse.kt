package com.example.pokemonapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    val count: String,
    val results: List<PokemonBase>
)

@Serializable
data class PokemonBase(
    val name: String,
    val url: String
)
