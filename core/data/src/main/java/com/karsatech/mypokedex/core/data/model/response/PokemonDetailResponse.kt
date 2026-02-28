package com.karsatech.mypokedex.core.data.model.response

import com.google.gson.annotations.SerializedName
import com.karsatech.mypokedex.core.common.ui.component.attr.PokeImageAttr.getImageUrl
import com.karsatech.mypokedex.core.data.source.local.model.PokemonEntity

data class PokemonDetailResponse(
    @SerializedName("name") val name: String? = null,
    @SerializedName("abilities") val abilities: List<Ability>? = null
) {
    data class Ability(
        @SerializedName("ability") val ability: AbilityDetail
    ) {
        data class AbilityDetail(
            @SerializedName("name") val abilityName: String,
            @SerializedName("url") val url: String
        )
    }

    fun mapToEntity(id: Int) = PokemonEntity(
        id = id,
        name = name.orEmpty(),
        url = getImageUrl(id),
        abilities = abilities?.map { it.ability.abilityName }.orEmpty()
    )
}