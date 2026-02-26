package com.karsatech.mypokedex.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.karsatech.mypokedex.core.navigation.base.BaseNavGraph
import com.karsatech.mypokedex.core.navigation.helper.composableScreen
import com.karsatech.mypokedex.core.navigation.route.AuthGraph.LoginRoute
import com.karsatech.mypokedex.core.navigation.route.AuthGraph.RegisterRoute
import com.karsatech.mypokedex.feature.auth.screen.LoginScreen
import com.karsatech.mypokedex.feature.auth.screen.RegisterScreen
import javax.inject.Inject

class AuthNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navController: NavController) {
        composableScreen<LoginRoute> {
            LoginScreen(navController)
        }
        composableScreen<RegisterRoute> {
            RegisterScreen(navController)
        }
    }
}