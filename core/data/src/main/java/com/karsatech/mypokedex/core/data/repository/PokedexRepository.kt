package com.karsatech.mypokedex.core.data.repository

import androidx.paging.PagingData
import com.karsatech.mypokedex.core.common.utils.state.ApiState
import com.karsatech.mypokedex.core.data.source.local.model.PokemonEntity
import com.karsatech.mypokedex.core.data.source.local.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {
    suspend fun registerUser(user: UserEntity)
    suspend fun loginUser(email: String, password: String): UserEntity?
    suspend fun isEmailExist(email: String): Boolean
    fun getUser(): Flow<UserEntity?>
    fun getPokemons(): Flow<PagingData<PokemonEntity>>
    fun getPokemonBySearch(query: String): Flow<PagingData<PokemonEntity>>
    fun getPokemonDetail(id: Int): Flow<ApiState<PokemonEntity>>
}