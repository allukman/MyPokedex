package com.karsatech.mypokedex.feature.profile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.karsatech.mypokedex.core.common.R
import com.karsatech.mypokedex.core.common.base.BaseScreen
import com.karsatech.mypokedex.core.common.ui.component.attr.AppTopBarAttr.TopBarArgs
import com.karsatech.mypokedex.core.common.ui.theme.AppTheme.typography
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp24
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp48
import com.karsatech.mypokedex.core.common.utils.state.collectAsStateValue
import com.karsatech.mypokedex.core.domain.model.User
import com.karsatech.mypokedex.core.navigation.helper.navigateTo
import com.karsatech.mypokedex.core.navigation.route.AuthGraph.LoginRoute
import com.karsatech.mypokedex.core.navigation.route.ProfileGraph.ProfileLandingRoute
import com.karsatech.mypokedex.feature.profile.viewmodel.ProfileViewModel

@Composable
internal fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) = with(viewModel) {
    val profileState = profileState.collectAsStateValue()
    var dataUser by remember { mutableStateOf<User?>(null) }

    BaseScreen(
        topBarArgs = TopBarArgs(
            title = {
                Text(
                    text = "Profile",
                    style = typography.h3
                )
            }
        )
    ) {
        LaunchedEffect(Unit) { getProfile() }

        LaunchedEffect(profileState) {
            profileState.handleUiState(
                onLoading = { },
                onSuccess = { user ->
                    dataUser = user
                },
                onFailed = { }
            )
        }

        dataUser?.let { user ->
            Column {
                Spacer(modifier = Modifier.height(Dp24))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .clickable { }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF6FA8FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.profile),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = user.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = user.email,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(Dp48))

                Button(
                    onClick = {
                        logout()
                        navController.navigateTo(
                            route = LoginRoute,
                            popUpTo = ProfileLandingRoute::class,
                            inclusive = true
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Text("Logout")
                }
            }
        }
    }
}