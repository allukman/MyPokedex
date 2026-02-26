package com.karsatech.mypokedex.core.data.repository

import com.karsatech.mypokedex.core.data.source.local.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {
    suspend fun registerUser(user: UserEntity)
    suspend fun loginUser(email: String, password: String): UserEntity?
    fun getUser(): Flow<UserEntity?>
}