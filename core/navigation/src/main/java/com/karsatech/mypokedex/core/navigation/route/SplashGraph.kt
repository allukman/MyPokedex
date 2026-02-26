package com.karsatech.mypokedex.core.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class SplashGraph {
    @Serializable
    data object SplashRoute : SplashGraph()
}