package com.karsatech.mypokedex.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.karsatech.mypokedex.core.navigation.base.BaseNavGraph
import com.karsatech.mypokedex.core.navigation.helper.composableScreen
import com.karsatech.mypokedex.core.navigation.route.HomeGraph.HomeDetailRoute
import com.karsatech.mypokedex.core.navigation.route.HomeGraph.HomeLandingRoute
import com.karsatech.mypokedex.feature.home.screen.HomeDetailScreen
import com.karsatech.mypokedex.feature.home.screen.HomeLandingScreen
import javax.inject.Inject

class HomeNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navController: NavController) {
        composableScreen<HomeLandingRoute> {
            HomeLandingScreen(navController)
        }
        composableScreen<HomeDetailRoute> {
            HomeDetailScreen(navController)
        }
    }
}