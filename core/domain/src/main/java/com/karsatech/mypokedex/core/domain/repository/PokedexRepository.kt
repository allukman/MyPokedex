package com.karsatech.mypokedex.core.domain.repository

import androidx.paging.PagingData
import com.karsatech.mypokedex.core.common.utils.state.ApiState
import com.karsatech.mypokedex.core.domain.model.Pokemon
import com.karsatech.mypokedex.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {
    suspend fun registerUser(user: User)
    suspend fun loginUser(email: String, password: String): User?
    suspend fun isEmailExist(email: String): Boolean
    fun getUser(): Flow<User?>
    fun getPokemons(): Flow<PagingData<Pokemon>>
    fun getPokemonBySearch(query: String): Flow<PagingData<Pokemon>>
    fun getPokemonDetail(id: Int): Flow<ApiState<Pokemon>>
}