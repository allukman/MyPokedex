package com.karsatech.mypokedex.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.karsatech.mypokedex.core.common.base.BaseRepository
import com.karsatech.mypokedex.core.common.utils.state.ApiState
import com.karsatech.mypokedex.core.common.utils.state.ApiState.Error
import com.karsatech.mypokedex.core.common.utils.state.ApiState.Loading
import com.karsatech.mypokedex.core.common.utils.state.ApiState.Success
import com.karsatech.mypokedex.core.data.mapper.toDomain
import com.karsatech.mypokedex.core.data.mapper.toEntity
import com.karsatech.mypokedex.core.data.source.local.db.PokedexDatabase
import com.karsatech.mypokedex.core.data.source.paging.PokedexRemoteMediator
import com.karsatech.mypokedex.core.data.source.remote.ApiService
import com.karsatech.mypokedex.core.domain.model.Pokemon
import com.karsatech.mypokedex.core.domain.model.User
import com.karsatech.mypokedex.core.domain.repository.PokedexRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PokedexRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: PokedexDatabase
) : PokedexRepository, BaseRepository() {

    override suspend fun registerUser(user: User) {
        db.userDao().registerUser(user.toEntity())
    }

    override suspend fun loginUser(email: String, password: String): User? {
        return db.userDao().loginUser(email, password)?.toDomain()
    }

    override suspend fun isEmailExist(email: String) =
        db.userDao().isEmailExist(email)

    override fun getUser(): Flow<User?> =
        db.userDao().getUser().map { it?.toDomain() }

    override fun getPokemons(): Flow<PagingData<Pokemon>> =
        Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = PokedexRemoteMediator(api, db),
            pagingSourceFactory = { db.pokemonDao().getPokemons() }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                entity.toDomain()
            }
        }

    override fun getPokemonBySearch(query: String): Flow<PagingData<Pokemon>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { db.pokemonDao().pagingSourceSearch(dbQuery) }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                entity.toDomain()
            }
        }
    }

    override fun getPokemonDetail(id: Int): Flow<ApiState<Pokemon>> =
        collectApiResult(
            fetchApi = { api.getPokemonDetail(id) },
            transformData = { it.mapToEntity(id) },
            saveToDb = { entity ->
                entity?.let { db.pokemonDao().insertPokemon(it) }
            },
            fetchFromDb = { db.pokemonDao().getPokemonByIdFlow(id) }
        ).map { state ->
            when (state) {
                is Success -> Success(state.data?.toDomain())
                is Error -> Error(state.throwable)
                is Loading -> Loading
            }
        }
}