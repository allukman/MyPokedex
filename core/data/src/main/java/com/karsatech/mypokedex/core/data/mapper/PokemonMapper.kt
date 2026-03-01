package com.karsatech.mypokedex.core.data.mapper

import com.karsatech.mypokedex.core.data.source.local.model.PokemonEntity
import com.karsatech.mypokedex.core.domain.model.Pokemon

fun PokemonEntity.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        url = url,
        abilities = abilities
    )
}

fun Pokemon.toEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        url = url,
        abilities = abilities
    )
}