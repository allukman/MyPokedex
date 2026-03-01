package com.karsatech.mypokedex.feature.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.karsatech.mypokedex.core.common.base.BaseViewModel
import com.karsatech.mypokedex.core.common.utils.state.UiState
import com.karsatech.mypokedex.core.common.utils.state.UiState.StateInitial
import com.karsatech.mypokedex.core.domain.model.Pokemon
import com.karsatech.mypokedex.core.domain.usecase.GetPokemonDetailUseCase
import com.karsatech.mypokedex.core.navigation.route.HomeGraph.HomeDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : BaseViewModel() {

    val args by lazy { savedStateHandle.toRoute<HomeDetailRoute>() }

    private val _pokemonDetailState =
        MutableStateFlow<UiState<Pokemon>>(StateInitial)

    val pokemonDetailState = _pokemonDetailState.asStateFlow()

    init {
        getPokemonDetail()
    }

    fun getPokemonDetail() = collectApiAsUiState(
        response = getPokemonDetailUseCase(args.pokemonId),
        updateState = { _pokemonDetailState.value = it }
    )
}