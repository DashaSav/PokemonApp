package com.example.pokemonapp.presentation.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.presentation.main.MainUiEvent
import com.example.pokemonapp.presentation.main.MainUiState
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FiltersBottomSheet(
    state: MainUiState,
    onEvent: (MainUiEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val sortOptions = state.sortOptions

    ModalBottomSheet(
        onDismissRequest = { onEvent(MainUiEvent.OnDismissBottomSheet) },
        sheetState = sheetState,
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Sort by",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
            Spacer(Modifier.height(5.dp))

            SingleChoiceSegmentedButtonRow {
                sortOptions.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = sortOptions.size
                        ),
                        onClick = { onEvent(MainUiEvent.OnSortSelect(index)) },
                        selected = index == state.selectedSortIndex,
                        label = { Text(label) }
                    )
                }
            }

            Spacer(Modifier.height(5.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Filter by",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
            Spacer(Modifier.height(5.dp))

            FlowRow {
                state.typeFilters.forEach { type ->
                    FilterChip(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = { onEvent(MainUiEvent.OnTypeSelect(type)) },
                        label = {
                            Text(type.name)
                        },
                        selected = type.selected,
                    )
                }
            }

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    onEvent(MainUiEvent.OnApplyFiltersClicked)

                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onEvent(MainUiEvent.OnDismissBottomSheet)
                        }
                    }
                }
            ) {
                Text("Apply filters")
            }
        }
    }
}

@Preview
@Composable
private fun FiltersBottomSheetPreview() {
    PokemonAppTheme {
        FiltersBottomSheet(
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