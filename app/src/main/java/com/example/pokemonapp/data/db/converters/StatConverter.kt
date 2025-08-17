package com.example.pokemonapp.data.db.converters

import androidx.room.TypeConverter
import com.example.pokemonapp.data.db.StatDB

class StatConverter {
    @TypeConverter
    fun fromString(stats: String): List<StatDB> {
        return stats.split(", ").map {
            val (baseStat, name) = it.split(":")

            StatDB(
                baseStat = baseStat.toInt(),
                name = name
            )
        }
    }

    @TypeConverter
    fun toString(stats: List<StatDB>): String {
        return stats.joinToString {
            it.baseStat.toString() + ":" + it.name
        }
    }
}
