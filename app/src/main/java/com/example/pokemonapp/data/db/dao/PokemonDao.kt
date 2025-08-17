package com.example.pokemonapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemonapp.data.db.PokemonEntity

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: List<PokemonEntity>)

    @Query("SELECT * FROM pokemons LIMIT :limit OFFSET :offset")
    suspend fun fetchPokemons(limit: Int = 20, offset: Int = 0): List<PokemonEntity>

    @Query("SELECT * FROM pokemons WHERE name LIKE :query LIMIT 1")
    suspend fun getPokemon(query: String): PokemonEntity?
}
