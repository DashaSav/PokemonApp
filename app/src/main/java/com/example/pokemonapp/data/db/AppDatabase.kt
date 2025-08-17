package com.example.pokemonapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokemonapp.data.db.converters.StatConverter
import com.example.pokemonapp.data.db.converters.TypesConverter
import com.example.pokemonapp.data.db.dao.PokemonDao

@Database(entities = [PokemonEntity::class], version = 1)
@TypeConverters(StatConverter::class, TypesConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
