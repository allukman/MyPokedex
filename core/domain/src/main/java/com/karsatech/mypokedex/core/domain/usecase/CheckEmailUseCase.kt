package com.karsatech.mypokedex.core.domain.usecase

import com.karsatech.mypokedex.core.domain.repository.PokedexRepository
import javax.inject.Inject

class CheckEmailUseCase @Inject constructor(
    private val repository: PokedexRepository
) {

    suspend operator fun invoke(email: String): Boolean {
        return repository.isEmailExist(email)
    }
}