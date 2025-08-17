package com.example.pokemonapp

import android.app.Application
import com.example.pokemonapp.di.DaggerAppComponent

class PokemonApplication : Application() {

    val appComponent = DaggerAppComponent.builder()
        .context(this)
        .build()
}