package com.karsatech.mypokedex.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.karsatech.mypokedex.core.navigation.base.BaseNavGraph
import com.karsatech.mypokedex.core.navigation.helper.composableScreen
import com.karsatech.mypokedex.core.navigation.route.HomeGraph.HomeDataClassRoute
import com.karsatech.mypokedex.core.navigation.route.HomeGraph.HomeDataTypeRoute
import com.karsatech.mypokedex.core.navigation.route.HomeGraph.HomeFetchApiRoute
import com.karsatech.mypokedex.core.navigation.route.HomeGraph.HomeLandingRoute
import com.karsatech.mypokedex.feature.home.screen.HomeDataClassScreen
import com.karsatech.mypokedex.feature.home.screen.HomeDataTypeScreen
import com.karsatech.mypokedex.feature.home.screen.HomeFetchApiScreen
import com.karsatech.mypokedex.feature.home.screen.HomeLandingScreen
import javax.inject.Inject

class HomeNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navController: NavController) {
        composableScreen<HomeLandingRoute> {
            HomeLandingScreen(navController)
        }
        composableScreen<HomeDataTypeRoute> {
            HomeDataTypeScreen(navController)
        }
        composableScreen<HomeDataClassRoute>(
            customArgs = HomeDataClassRoute.typeMap
        ) {
            HomeDataClassScreen(navController)
        }
        composableScreen<HomeFetchApiRoute> {
            HomeFetchApiScreen(navController)
        }
    }
}