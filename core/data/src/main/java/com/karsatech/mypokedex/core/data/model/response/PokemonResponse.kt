package com.karsatech.mypokedex.core.data.model.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("next") val next: String? = null,
    @SerializedName("previous") val previous: String? = null,
    @SerializedName("results") val results: List<PokemonResult>? = null,
) {
    data class PokemonResult(
        @SerializedName("name") val name: String? = null,
        @SerializedName("url") val url: String? = null
    ) {
        val id: Int
            get() = url?.substringAfterLast("/")?.toIntOrNull() ?: 0
    }
}