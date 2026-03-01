package com.karsatech.mypokedex.core.data.source.remote

import com.karsatech.mypokedex.core.data.model.response.PokemonDetailResponse
import com.karsatech.mypokedex.core.data.model.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int
    ): PokemonResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: Int
    ): PokemonDetailResponse
}