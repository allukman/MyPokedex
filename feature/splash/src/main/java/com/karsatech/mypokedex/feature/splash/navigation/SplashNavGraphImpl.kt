package com.karsatech.mypokedex.feature.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.karsatech.mypokedex.core.navigation.base.BaseNavGraph
import com.karsatech.mypokedex.core.navigation.helper.composableScreen
import com.karsatech.mypokedex.core.navigation.route.SplashGraph
import com.karsatech.mypokedex.feature.splash.screen.SplashScreen
import javax.inject.Inject

class SplashNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navController: NavController) {
        composableScreen<SplashGraph.SplashRoute> { SplashScreen(navController) }
    }
}