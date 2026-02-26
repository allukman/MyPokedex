package com.karsatech.mypokedex.core.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthGraph {
    @Serializable
    data object LoginRoute : HomeGraph()

    @Serializable
    data object RegisterRoute : HomeGraph()

}