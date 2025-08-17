package com.example.pokemonapp.data.db.converters

import androidx.room.TypeConverter
import com.example.pokemonapp.data.db.TypeDB

class TypesConverter {
    @TypeConverter
    fun fromString(types: String): List<TypeDB> {
        return types.split(", ").map {
            TypeDB(name = it)
        }
    }

    @TypeConverter
    fun toString(types: List<TypeDB>): String {
        return types.joinToString { it.name }
    }
}
