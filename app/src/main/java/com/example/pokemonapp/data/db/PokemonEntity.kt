package com.example.pokemonapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("pokemons")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "stats") val stats: List<StatDB>,
    @ColumnInfo(name = "types") val types: List<TypeDB>
)

data class StatDB(
    val baseStat: Int,
    val name: String
)

data class TypeDB(
    val name: String
)
