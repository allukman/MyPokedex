package com.karsatech.mypokedex.feature.auth.screen

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.karsatech.mypokedex.core.common.base.BaseScreen
import com.karsatech.mypokedex.core.common.ui.component.PokeTextfield
import com.karsatech.mypokedex.core.common.ui.theme.AppTheme.typography
import com.karsatech.mypokedex.core.common.ui.theme.Constant.EMPTY_STRING
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp2
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp24
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp8
import com.karsatech.mypokedex.core.common.utils.LocalActivity
import com.karsatech.mypokedex.core.common.utils.state.collectAsStateValue
import com.karsatech.mypokedex.core.navigation.helper.navigateTo
import com.karsatech.mypokedex.core.navigation.route.AuthGraph.LoginRoute
import com.karsatech.mypokedex.core.navigation.route.AuthGraph.RegisterRoute
import com.karsatech.mypokedex.core.navigation.route.HomeGraph.HomeLandingRoute
import com.karsatech.mypokedex.feature.auth.viewmodel.AuthViewModel

@Composable
internal fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) = with(viewModel) {
    val authState = authState.collectAsStateValue()
    val context = LocalContext.current
    val activity = LocalActivity.current

    var email by remember { mutableStateOf(EMPTY_STRING) }
    var password by remember { mutableStateOf(EMPTY_STRING) }

    var showLoading by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(EMPTY_STRING) }

    BackHandler { activity.finish() }

    BaseScreen(showDefaultTopBar = false) {

        LaunchedEffect(authState) {
            authState.handleUiState(
                onLoading = { showLoading = true },
                onSuccess = {
                    showLoading = false
                    navController.navigateTo(
                        route = HomeLandingRoute,
                        popUpTo = LoginRoute::class,
                        inclusive = true,
                        launchSingleTop = true
                    )
                    resetAuthState()
                    Toast.makeText(context, "Login success", LENGTH_SHORT).show()
                },
                onFailed = {
                    showLoading = false
                    showError = it.message ?: "Login failed"
                    resetAuthState()
                }
            )
        }

        LaunchedEffect(showError) {
            if (showError.isNotBlank()) Toast.makeText(
                context,
                showError,
                LENGTH_SHORT
            ).show()
            showError = EMPTY_STRING
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Sign In", style = typography.bodyBold1, fontSize = 24.sp)

            Spacer(modifier = Modifier.height(Dp8))

            Text("Proceed to access your account", style = typography.bodyBold3)

            Spacer(modifier = Modifier.height(48.dp))

            PokeTextfield(
                label = "Email",
                placeholder = "example@gmail.com",
                value = email,
                onValueChange = { email = it }
            )

            PokeTextfield(
                label = "Password",
                placeholder = "****************",
                isPassword = true,
                value = password,
                onValueChange = { password = it }
            )

            Text(
                "Forgot Password?",
                modifier = Modifier.align(Alignment.End),
                style = typography.bodyBold3
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Please fill all fields", LENGTH_SHORT).show()
                        return@Button
                    } else {
                        viewModel.login(email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors()
            ) {
                if (showLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(Dp24),
                        strokeWidth = Dp2
                    )
                } else {
                    Text("Sign In")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row {
                Text(text = "Don't have an account? ", style = typography.bodyBold3)
                Text(
                    text = "Sign up",
                    color = MaterialTheme.colorScheme.primary,
                    style = typography.bodyBold3,
                    modifier = Modifier.clickable { navController.navigateTo(RegisterRoute) }
                )
            }

        }
    }
}