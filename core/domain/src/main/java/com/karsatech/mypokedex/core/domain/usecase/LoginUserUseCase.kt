package com.karsatech.mypokedex.core.domain.usecase

import com.karsatech.mypokedex.core.domain.repository.PokedexRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: PokedexRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        repository.loginUser(email, password)
}