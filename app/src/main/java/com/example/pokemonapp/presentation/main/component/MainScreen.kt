package com.example.pokemonapp.presentation.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemonapp.R
import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.presentation.main.MainUiEvent
import com.example.pokemonapp.presentation.main.MainUiState
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(state: MainUiState, onEvent: (MainUiEvent) -> Unit) {
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        state = refreshState,
        onRefresh = { onEvent(MainUiEvent.OnRefresh) }
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.Black)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 20.dp)
                .fillMaxSize()
        ) {
            MainScreenContent(onEvent, state)
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center))
        }
    }

    if (state.showBottomSheet) {
        FiltersBottomSheet(state, onEvent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnScope.MainScreenContent(
    onEvent: (MainUiEvent) -> Unit,
    state: MainUiState
) {
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(lazyGridState) {
        snapshotFlow { lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex != null && lastVisibleItemIndex >= lazyGridState.layoutInfo.totalItemsCount - 1) {
                    onEvent(MainUiEvent.OnNextPageLoad)
                }
            }
    }

    Image(
        modifier = Modifier
            .width(190.dp)
            .height(80.dp)
            .align(Alignment.CenterHorizontally),

        painter = painterResource(R.drawable.pokemon_logo),
        contentDescription = "Pokemon",
    )

    SearchField(onEvent, state)
    Spacer(Modifier.height(10.dp))

    if (state.pokemons.isNotEmpty()) {
        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            content = {
                items(state.pokemons) { item ->
                    PokemonItem(
                        modifier = Modifier.padding(8.dp),
                        pokemon = item,
                        onPokemonClicked = { onEvent(MainUiEvent.OnItemClicked(item)) }
                    )
                }
            }
        )
    } else if (!state.isLoading && !state.isRefreshing) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Nothing found :(", fontSize = 30.sp, color = Color.White)
        }
    }
}

@Composable
private fun SearchField(
    onEvent: (MainUiEvent) -> Unit,
    state: MainUiState,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Black,
            unfocusedContainerColor = Color.Black,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            focusedTrailingIconColor = Color.White,
            unfocusedTrailingIconColor = Color.White,
        ),
        onValueChange = { onEvent(MainUiEvent.OnSearchChanged(it)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onEvent(MainUiEvent.OnSearchClicked)
                focusManager.clearFocus()
            }
        ),
        value = state.search,
        label = {
            Text(text = "Поиск")
        },
        placeholder = {
            Text(text = "Введите имя покемона")
        },
        maxLines = 1,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "",
                modifier = Modifier.clickable { onEvent(MainUiEvent.OnFiltersClicked) }
            )
        }
    )
}

@Preview
@Composable
private fun MainScreenPreview() {
    PokemonAppTheme {
        MainScreen(
            state = MainUiState(
                pokemons = listOf(
                    Pokemon(
                        id = 0,
                        name = "pikachu",
                        imageUrl = "",
                        types = emptyList(),
                        stats = emptyList(),
                    )
                )
            )
        ) {}
    }
}