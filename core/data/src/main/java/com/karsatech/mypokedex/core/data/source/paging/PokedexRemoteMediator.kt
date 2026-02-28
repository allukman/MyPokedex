package com.karsatech.mypokedex.core.data.source.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH
import androidx.paging.RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH
import androidx.room.withTransaction
import com.karsatech.mypokedex.core.data.source.local.db.PokedexDatabase
import com.karsatech.mypokedex.core.data.source.local.model.PokemonEntity
import com.karsatech.mypokedex.core.data.source.local.model.RemoteKeysEntity
import com.karsatech.mypokedex.core.data.source.remote.ApiService
import java.util.concurrent.TimeUnit.HOURS
import kotlin.text.orEmpty
import kotlin.toString

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokeApiService: ApiService,
    private val pokemonDatabase: PokedexDatabase
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = HOURS.toMillis(3)

        val lastUpdated = pokemonDatabase.remoteKeysDao().getCreationTime() ?: 0L
        val isCacheStale = (System.currentTimeMillis() - lastUpdated) > cacheTimeout

        return if (isCacheStale) LAUNCH_INITIAL_REFRESH else SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = false)
                    val remoteKeys =
                        pokemonDatabase.remoteKeysDao().remoteKeysPokemonId(lastItem.id)
                    remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            val response = pokeApiService.getPokemonList(loadKey, state.config.pageSize)
            val pokemonList = response.results
            val endOfPaginationReached = pokemonList.isNullOrEmpty()

            pokemonDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDatabase.remoteKeysDao().clearRemoteKeys()
                    pokemonDatabase.pokedexDao().clearPokemons()
                }

                val prevKey = if (loadKey == 0) null else loadKey - state.config.pageSize
                val nextKey = if (endOfPaginationReached) null else loadKey + state.config.pageSize
                val entities = pokemonList?.map { result ->
                    val id = result.url?.split("/")?.dropLast(1)?.last()?.toInt() ?: 0
                    PokemonEntity(
                        id = id,
                        name = result.name.orEmpty(),
                        url = result.url.orEmpty()
                    )
                }
                val keys = entities?.map {
                    RemoteKeysEntity(
                        pokemonId = it.id.toString(),
                        nextKey = nextKey,
                        prevKey = prevKey,
                        createdAt = System.currentTimeMillis()
                    )
                }

                pokemonDatabase.pokedexDao().insertPokemons(entities.orEmpty())
                pokemonDatabase.remoteKeysDao().insertKeys(keys.orEmpty())
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}