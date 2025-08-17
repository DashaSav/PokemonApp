package com.example.pokemonapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokemonapp.presentation.main.MainViewModel
import com.example.pokemonapp.presentation.main.component.MainScreen

@Composable
fun PokemonAppNavigation(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "mainScreen") {
        composable(route = "mainScreen") {
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            MainScreen(
                onEvent = viewModel::onEvent,
                state = state
            )
        }
    }
}