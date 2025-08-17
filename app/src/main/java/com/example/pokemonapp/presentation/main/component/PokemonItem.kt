package com.example.pokemonapp.presentation.main.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.pokemonapp.domain.model.Pokemon

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    onPokemonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(120.dp),
        onClick = onPokemonClicked,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFF5722),
            contentColor = Color.White
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = "pokemon image",
                modifier = Modifier.size(90.dp)
            )

            Text(
                text = pokemon.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

}