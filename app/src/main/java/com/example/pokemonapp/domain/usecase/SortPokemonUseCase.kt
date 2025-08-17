package com.example.pokemonapp.domain.usecase

import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.domain.model.SortType
import javax.inject.Inject

class SortPokemonUseCase @Inject constructor() {
    private val hpComparator = statComparator("hp")
    private val defComparator = statComparator("defense")
    private val attackComparator = statComparator("attack")
    private val nameComparator = compareBy<Pokemon> { it.name }
    private val idComparator = compareBy<Pokemon> { it.id }

    fun sortPokemons(pokemons: List<Pokemon>, sortBy: SortType): List<Pokemon> {
        return when(sortBy){
            SortType.ID -> pokemons.sortedWith(idComparator)
            SortType.NAME -> pokemons.sortedWith(nameComparator)
            SortType.HP -> pokemons.sortedWith(hpComparator)
            SortType.ATTACK -> pokemons.sortedWith(attackComparator)
            SortType.DEF -> pokemons.sortedWith(defComparator)
        }
    }

    private fun statComparator(stat: String): Comparator<Pokemon> = compareBy { pokemon ->
        pokemon.stats.find { it.name == stat }?.baseStat
    }
}
