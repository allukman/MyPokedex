package com.karsatech.mypokedex.feature.auth.screen

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.karsatech.mypokedex.core.common.R
import com.karsatech.mypokedex.core.common.base.BaseScreen
import com.karsatech.mypokedex.core.common.ui.component.PokeTextfield
import com.karsatech.mypokedex.core.common.ui.theme.AppTheme.typography
import com.karsatech.mypokedex.core.common.ui.theme.Constant.EMPTY_STRING
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp2
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp24
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp8
import com.karsatech.mypokedex.core.common.utils.state.UiState
import com.karsatech.mypokedex.core.common.utils.state.UiState.StateLoading
import com.karsatech.mypokedex.core.common.utils.state.collectAsStateValue
import com.karsatech.mypokedex.core.data.source.local.model.UserEntity
import com.karsatech.mypokedex.core.navigation.helper.navigateTo
import com.karsatech.mypokedex.core.navigation.route.AuthGraph.LoginRoute
import com.karsatech.mypokedex.core.navigation.route.AuthGraph.RegisterRoute
import com.karsatech.mypokedex.feature.auth.viewmodel.AuthViewModel

@Composable
internal fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) = with(viewModel) {
    val authState = authState.collectAsStateValue()
    val context = LocalContext.current

    var name by remember { mutableStateOf(EMPTY_STRING) }
    var email by remember { mutableStateOf(EMPTY_STRING) }
    var password by remember { mutableStateOf(EMPTY_STRING) }

    LaunchedEffect(authState) {
        when (authState) {

            is UiState.StateSuccess -> {
                navController.navigateTo(
                    route = LoginRoute,
                    popUpTo = RegisterRoute::class,
                    inclusive = true
                )
                Toast.makeText(
                    context,
                    R.string.register_success,
                    LENGTH_SHORT
                ).show()
                viewModel.resetAuthState()
            }

            is UiState.StateFailed -> {
                Toast.makeText(
                    context,
                    authState.throwable.message,
                    LENGTH_SHORT
                ).show()
                viewModel.resetAuthState()
            }

            else -> Unit
        }
    }

    BaseScreen(
        showDefaultTopBar = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.create_an_account),
                style = typography.bodyBold1,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(Dp8))

            Text(
                text = stringResource(R.string.sign_up_description),
                style = typography.bodyBold3
            )

            Spacer(modifier = Modifier.height(48.dp))

            PokeTextfield(
                label = stringResource(R.string.label_name),
                placeholder = stringResource(R.string.label_name_placeholder),
                value = name,
                onValueChange = { name = it }
            )

            PokeTextfield(
                label = stringResource(R.string.label_email),
                placeholder = stringResource(R.string.label_email_placeholder),
                value = email,
                onValueChange = { email = it }
            )

            PokeTextfield(
                label = stringResource(R.string.label_password),
                placeholder = stringResource(R.string.label_password_placeholder),
                isPassword = true,
                value = password,
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (name.isBlank() || email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, R.string.fill_all_fields, LENGTH_SHORT).show()
                    } else {
                        val user = UserEntity(
                            name = name,
                            email = email,
                            password = password
                        )
                        viewModel.register(user)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors()
            ) {
                if (authState is StateLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(Dp24),
                        color = Color.White,
                        strokeWidth = Dp2
                    )
                } else {
                    Text(
                        text = stringResource(R.string.sign_up),
                        style = typography.bodyBold3
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row {
                Text(
                    text = stringResource(R.string.sign_in_instead),
                    style = typography.bodyBold3
                )
                Text(
                    text = stringResource(R.string.sign_in),
                    color = MaterialTheme.colorScheme.primary,
                    style = typography.bodyBold3,
                    modifier = Modifier.clickable {
                        navController.navigateTo(
                            route = LoginRoute,
                            popUpTo = RegisterRoute::class,
                            inclusive = true
                        )
                    }
                )
            }

        }
    }
}