package com.karsatech.mypokedex.core.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed class InfoGraph {
    @Serializable
    data object InfoLandingRoute : InfoGraph()
}