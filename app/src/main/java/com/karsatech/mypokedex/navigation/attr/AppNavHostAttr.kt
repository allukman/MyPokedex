package com.karsatech.mypokedex.navigation.attr

import com.karsatech.mypokedex.core.common.R
import com.karsatech.mypokedex.core.navigation.route.HomeGraph.HomeLandingRoute
import com.karsatech.mypokedex.core.navigation.route.ProfileGraph.ProfileLandingRoute

object AppNavHostAttr {
    data class BottomNavItem<T : Any>(
        val route: T,
        val icon: Int,
        val label: String
    )

    fun getBottomNav() = listOf(
        BottomNavItem(route = HomeLandingRoute, icon = R.drawable.ic_home, label = "Home"),
        BottomNavItem(route = ProfileLandingRoute, icon = R.drawable.ic_profile, label = "Profile")
    )
}