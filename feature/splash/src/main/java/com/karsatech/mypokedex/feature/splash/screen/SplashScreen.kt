package com.karsatech.mypokedex.feature.splash.screen

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
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
    val notifPermission = rememberLauncherForActivityResult(RequestPermission()) {}
    BaseScreen(showDefaultTopBar = false) {
        LaunchedEffect(Unit) {
            if (SDK_INT >= TIRAMISU) notifPermission.launch(POST_NOTIFICATIONS)
        }

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