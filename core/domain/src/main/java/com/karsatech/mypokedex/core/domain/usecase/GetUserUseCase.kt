package com.karsatech.mypokedex.core.domain.usecase

import com.karsatech.mypokedex.core.domain.model.User
import com.karsatech.mypokedex.core.domain.repository.PokedexRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: PokedexRepository
) {

    operator fun invoke(): Flow<User?> {
        return repository.getUser()
    }
}