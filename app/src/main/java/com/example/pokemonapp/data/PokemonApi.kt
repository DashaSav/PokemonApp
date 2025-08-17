package com.example.pokemonapp.data

import com.example.pokemonapp.data.model.Pokemon
import com.example.pokemonapp.data.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/")
    suspend fun getAllPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 20
    ): PokemonResponse

    @GET("pokemon/{id}/")
    suspend fun getPokemon(@Path("id") id: Int): Pokemon

    @GET("pokemon/{name}/")
    suspend fun getPokemon(@Path("name") name: String): Pokemon?
}
