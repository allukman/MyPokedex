package com.karsatech.mypokedex.core.domain.usecase

import com.karsatech.mypokedex.core.domain.repository.PokedexRepository
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(
    private val repository: PokedexRepository
) {
    operator fun invoke(query: String) =
        repository.getPokemonBySearch(query)
}