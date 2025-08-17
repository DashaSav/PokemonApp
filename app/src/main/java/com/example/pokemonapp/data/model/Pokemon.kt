package com.example.pokemonapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
)

@Serializable
data class Sprites(
    @SerialName("front_default")
    val frontDefault: String,
)

@Serializable
data class Type(
    @SerialName("type")
    val name: Name
)

@Serializable
data class Stat(
    @SerialName("base_stat")
    val baseStat: Int,
    @SerialName("stat")
    val name: Name
)

@Serializable
data class Name(
    val name: String
)