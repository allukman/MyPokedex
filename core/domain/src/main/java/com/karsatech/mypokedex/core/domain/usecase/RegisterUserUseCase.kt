package com.karsatech.mypokedex.core.domain.usecase

import com.karsatech.mypokedex.core.domain.model.User
import com.karsatech.mypokedex.core.domain.repository.PokedexRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: PokedexRepository
) {
    suspend operator fun invoke(user: User) {
        repository.registerUser(user)
    }
}