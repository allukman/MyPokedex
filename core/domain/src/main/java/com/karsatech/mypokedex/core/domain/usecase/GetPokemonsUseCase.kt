package com.karsatech.mypokedex.core.domain.usecase

import com.karsatech.mypokedex.core.domain.repository.PokedexRepository
import javax.inject.Inject

class GetPokemonsUseCase @Inject constructor(
    private val repository: PokedexRepository
) {
    operator fun invoke() = repository.getPokemons()
}