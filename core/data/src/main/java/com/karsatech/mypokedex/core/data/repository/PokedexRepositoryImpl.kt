package com.karsatech.mypokedex.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.karsatech.mypokedex.core.common.base.BaseRepository
import com.karsatech.mypokedex.core.data.source.local.db.PokedexDatabase
import com.karsatech.mypokedex.core.data.source.local.model.PokemonEntity
import com.karsatech.mypokedex.core.data.source.local.model.UserEntity
import com.karsatech.mypokedex.core.data.source.paging.PokedexRemoteMediator
import com.karsatech.mypokedex.core.data.source.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PokedexRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: PokedexDatabase
) : PokedexRepository, BaseRepository() {
    override suspend fun registerUser(user: UserEntity) = db.userDao().registerUser(user)

    override suspend fun loginUser(email: String, password: String): UserEntity? =
        db.userDao().loginUser(email, password)

    override suspend fun isEmailExist(email: String) = db.userDao().isEmailExist(email)

    override fun getUser() = db.userDao().getUser()

    override fun getPokemons() = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = PokedexRemoteMediator(api, db),
        pagingSourceFactory = { db.pokemonDao().getPokemons() }
    ).flow

    override fun getPokemonBySearch(query: String): Flow<PagingData<PokemonEntity>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { db.pokemonDao().pagingSourceSearch(dbQuery) }
        ).flow
    }

    override fun getPokemonDetail(id: Int) = collectApiResult(
        fetchApi = { api.getPokemonDetail(id) },
        transformData = { it.mapToEntity(id) },
        saveToDb = { it?.let { pokemon -> db.pokemonDao().insertPokemon(pokemon) } },
        fetchFromDb = { db.pokemonDao().getPokemonByIdFlow(id) }
    )
}