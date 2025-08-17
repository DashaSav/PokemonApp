package com.example.pokemonapp.data

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitHelper {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private var instance: Retrofit? = null

    fun getInstance(): Retrofit {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        if (instance == null) {
            instance = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
        }

        return instance!!
    }
}
