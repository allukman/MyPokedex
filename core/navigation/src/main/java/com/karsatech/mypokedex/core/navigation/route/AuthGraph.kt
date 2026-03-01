package com.karsatech.mypokedex.core.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthGraph {
    @Serializable
    data object LoginRoute : AuthGraph()

    @Serializable
    data object RegisterRoute : AuthGraph()

}