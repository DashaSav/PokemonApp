package com.example.pokemonapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun PokemonItem(color: Color, name: String, image: Int) {
    Card(modifier = Modifier.background(color = color),
        ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                contentDescription = "pokemon image",
                painter = painterResource(image)
            )
            Text(
                text = name,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

}