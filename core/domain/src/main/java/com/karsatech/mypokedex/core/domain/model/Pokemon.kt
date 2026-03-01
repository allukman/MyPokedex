package com.karsatech.mypokedex.core.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val url: String,
    val abilities: List<String>? = null
)