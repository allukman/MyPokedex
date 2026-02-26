package com.karsatech.mypokedex.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.karsatech.mypokedex.core.navigation.base.BaseNavGraph
import com.karsatech.mypokedex.core.navigation.helper.composableScreen
import com.karsatech.mypokedex.core.navigation.route.ProfileGraph
import com.karsatech.mypokedex.feature.profile.screen.ProfileScreen
import javax.inject.Inject

class ProfileNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navController: NavController) {
        composableScreen<ProfileGraph.ProfileLandingRoute> { ProfileScreen(navController) }
    }
}