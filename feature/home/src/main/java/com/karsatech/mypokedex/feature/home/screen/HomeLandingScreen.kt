package com.karsatech.mypokedex.feature.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.karsatech.mypokedex.core.common.R
import com.karsatech.mypokedex.core.common.base.BaseScreen
import com.karsatech.mypokedex.core.common.ui.component.PokeCard
import com.karsatech.mypokedex.core.common.ui.component.PokeTextfield
import com.karsatech.mypokedex.core.common.ui.theme.AppTheme.typography
import com.karsatech.mypokedex.core.common.ui.theme.Constant.EMPTY_STRING
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp16
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp32

@Composable
internal fun HomeLandingScreen(navController: NavController) {
    var querySearch by remember { mutableStateOf(EMPTY_STRING) }

    BaseScreen(showDefaultTopBar = false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(Dp32))
            Text(
                text = "MyPokédex",
                style = typography.h1
            )
            Spacer(modifier = Modifier.height(Dp16))
            Text(
                text = "Search for a Pokémon by name or using its National Pokédex number.",
                style = typography.body2,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(Dp16))
            PokeTextfield(
                placeholder = "Name or number",
                value = querySearch,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                onValueChange = { querySearch = it }
            )

            Spacer(modifier = Modifier.height(Dp16))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dp16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PokeCard(
                    name = "Bulbasaur",
                    number = "001",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
                )
                PokeCard(
                    name = "Charmander",
                    number = "004",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png"
                )
            }

            Spacer(modifier = Modifier.height(Dp16))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dp16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PokeCard(
                    name = "Charmeleon",
                    number = "005",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png"
                )
                PokeCard(
                    name = "Kakuna",
                    number = "014",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/14.png"
                )
            }
        }
    }
}