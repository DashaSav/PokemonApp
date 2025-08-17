package com.example.pokemonapp.domain.model

data class Pokemon(
    val id: Int,
    val name: String = "",
    val imageUrl: String,
    val types: List<Type>,
    val stats: List<Stat>
)

data class Type(
    val type: String
)

data class Stat(
    val baseStat: Int,
    val name: String
)
