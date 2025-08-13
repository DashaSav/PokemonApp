package com.example.pokemonapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.R

@Composable
fun MainScreen() {
    Column (
        modifier = Modifier
        .background(color = Color.Black)
    ) {
        Image(
            painter = painterResource(R.drawable.pokemon_logo),
            contentDescription = "Pokemon",
        )

        //search

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            content = {
                pokemonList(dataList) { item ->
                    Card(
                        modifier = Modifier.padding(4.dp)
                    ) {
                        PokemonItem
                    }
                }
            }
        )
    }
}