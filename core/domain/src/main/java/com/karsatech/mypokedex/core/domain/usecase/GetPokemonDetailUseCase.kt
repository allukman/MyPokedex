package com.karsatech.mypokedex.core.domain.usecase

import com.karsatech.mypokedex.core.domain.repository.PokedexRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokedexRepository
) {
    operator fun invoke(id: Int) =
        repository.getPokemonDetail(id)
}