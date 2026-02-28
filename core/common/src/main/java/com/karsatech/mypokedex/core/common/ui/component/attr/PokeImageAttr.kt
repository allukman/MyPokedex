package com.karsatech.mypokedex.core.common.ui.component.attr

object PokeImageAttr {
    fun getImageUrl(
        id: Int
    ) = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
}