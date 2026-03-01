package com.karsatech.mypokedex.feature.splash.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.karsatech.mypokedex.core.common.R
import com.karsatech.mypokedex.core.common.base.BaseScreen
import com.karsatech.mypokedex.core.common.ui.theme.AppTheme.typography
import com.karsatech.mypokedex.core.common.utils.state.collectAsStateValue
import com.karsatech.mypokedex.core.navigation.helper.navigateTo
import com.karsatech.mypokedex.core.navigation.route.AuthGraph.LoginRoute
import com.karsatech.mypokedex.core.navigation.route.HomeGraph.HomeLandingRoute
import com.karsatech.mypokedex.core.navigation.route.SplashGraph.SplashRoute
import com.karsatech.mypokedex.feature.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

@Composable
internal fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) = with(viewModel) {
    val token = token.collectAsStateValue()
    BaseScreen(showDefaultTopBar = false) {

        val currentTime = System.currentTimeMillis() / 1000

        LaunchedEffect(token) {
            delay(1000)
            navController.navigateTo(
                route = if (currentTime < token) HomeLandingRoute else LoginRoute,
                popUpTo = SplashRoute::class,
                inclusive = true
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Center
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = typography.h1,
                color = colorScheme.primary
            )
        }
    }
}