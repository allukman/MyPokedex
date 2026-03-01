package com.karsatech.mypokedex.feature.home.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.karsatech.mypokedex.core.common.base.BaseViewModel
import com.karsatech.mypokedex.core.data.repository.PokedexRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeLandingViewModel @Inject constructor(
    repository: PokedexRepository
) : BaseViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonResult = _searchQuery.flatMapLatest { query ->
        if (query.isBlank()) {
            repository.getPokemons().cachedIn(viewModelScope)
        } else {
            repository.getPokemonBySearch(query).cachedIn(viewModelScope)
        }
    }

}