package com.karsatech.mypokedex.core.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class HomeGraph {
    @Serializable
    data object HomeLandingRoute : HomeGraph()

    @Serializable
    data class HomeDetailRoute(
        val pokemonId: Int,
        val pokemonName: String
    ) : HomeGraph()
}