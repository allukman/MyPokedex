package com.karsatech.mypokedex.feature.home.screen

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.karsatech.mypokedex.core.common.R
import com.karsatech.mypokedex.core.common.base.BaseScreen
import com.karsatech.mypokedex.core.common.ui.component.PokeCard
import com.karsatech.mypokedex.core.common.ui.component.PokeTextfield
import com.karsatech.mypokedex.core.common.ui.theme.AppTheme.typography
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp120
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp16
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp32
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp8
import com.karsatech.mypokedex.core.common.utils.onCustomClick
import com.karsatech.mypokedex.core.navigation.helper.navigateTo
import com.karsatech.mypokedex.core.navigation.route.HomeGraph
import com.karsatech.mypokedex.feature.home.viewmodel.HomeLandingViewModel

@Composable
internal fun HomeLandingScreen(
    navController: NavController,
    viewModel: HomeLandingViewModel = hiltViewModel()
) = with(viewModel) {

    val query = searchQuery.collectAsState()
    val lazyPokemonItems = pokemonResult.collectAsLazyPagingItems()

    BaseScreen(showDefaultTopBar = false) {

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = Adaptive(minSize = Dp120),
            verticalArrangement = spacedBy(Dp8),
            horizontalArrangement = spacedBy(Dp8),
        ) {

            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
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
                        value = query.value,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null
                            )
                        },
                        onValueChange = { viewModel.onSearchQueryChanged(it) }
                    )

                    Spacer(modifier = Modifier.height(Dp16))
                }
            }

            when (val refreshState = lazyPokemonItems.loadState.refresh) {

                is LoadState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dp32),
                            contentAlignment = Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dp16),
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Text(
                                text = "Failed to fetch data: ${refreshState.error.localizedMessage}",
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(Dp8))
                            Button(onClick = { lazyPokemonItems.retry() }) {
                                Text("Retry")
                            }
                        }
                    }
                }

                else -> {

                    if (lazyPokemonItems.itemCount == 0) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(Dp32),
                                contentAlignment = Center
                            ) {
                                Text(
                                    text = "Pokémon not found",
                                    style = typography.bodyBold1
                                )
                            }
                        }
                    } else {
                        items(
                            count = lazyPokemonItems.itemCount,
                            key = lazyPokemonItems.itemKey { it.id }
                        ) { index ->

                            val pokemon = lazyPokemonItems[index]

                            pokemon?.let {
                                PokeCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .onCustomClick {
                                            navController.navigateTo(
                                                HomeGraph.HomeDetailRoute(
                                                    pokemonId = it.id,
                                                    pokemonName = it.name
                                                )
                                            )
                                        },
                                    id = it.id,
                                    name = it.name
                                )
                            }
                        }

                        when (lazyPokemonItems.loadState.append) {

                            is LoadState.Loading -> {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(Dp16),
                                        contentAlignment = Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }

                            is LoadState.Error -> {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(Dp16),
                                        horizontalAlignment = CenterHorizontally
                                    ) {
                                        Text("Failed to load more")
                                        Spacer(modifier = Modifier.height(Dp8))
                                        Button(onClick = { lazyPokemonItems.retry() }) {
                                            Text("Try Again")
                                        }
                                    }
                                }
                            }

                            else -> Unit
                        }
                    }
                }
            }
        }
    }
}

//@Composable
//private fun PokedexScreen() {
//    when (val refreshState = lazyPokemonItems.loadState.refresh) {
//        is LoadState.Loading -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
//                CircularProgressIndicator(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentWidth(CenterHorizontally)
//                )
//            }
//        }
//
//        is LoadState.Error -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
//                Column(horizontalAlignment = CenterHorizontally) {
//                    Text(
//                        text = "Failed to fetch data: ${refreshState.error.localizedMessage}",
//                        textAlign = TextAlign.Center
//                    )
//                    Spacer(modifier = Modifier.height(Dp8))
//                    Button(onClick = { lazyPokemonItems.retry() }) {
//                        Text("Retry")
//                    }
//                }
//            }
//        }
//
//        else -> if (lazyPokemonItems.itemCount == 0) {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Center
//            ) {
//                Text(
//                    text = "Pokémon not found",
//                    style = typography.bodyBold1
//                )
//            }
//        } else LazyVerticalGrid(
//            modifier = Modifier.fillMaxSize(),
//            columns = Adaptive(minSize = Dp120),
//            verticalArrangement = spacedBy(Dp8),
//            horizontalArrangement = spacedBy(Dp8),
//            contentPadding = PaddingValues(bottom = Dp100)
//        ) {
//            items(
//                count = lazyPokemonItems.itemCount,
//                key = lazyPokemonItems.itemKey { it.id }
//            ) { index ->
//                val pokemon = lazyPokemonItems[index]
//                pokemon?.let {
//                    PokeCard(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .onCustomClick {
//                                navController.navigateTo(
//                                    HomeDetailRoute(pokemonId = pokemon.id)
//                                )
//                            },
//                        id = pokemon.id,
//                        name = pokemon.name
//                    )
//                }
//            }
//
//            item(span = { GridItemSpan(maxLineSpan) }) {
//                when (lazyPokemonItems.loadState.append) {
//                    is LoadState.Loading -> {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(Dp16),
//                            horizontalArrangement = Arrangement.Center
//                        ) { CircularProgressIndicator() }
//                    }
//
//                    is LoadState.Error -> {
//                        Column(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalAlignment = CenterHorizontally
//                        ) {
//                            Text("Failed to load more")
//                            Button(onClick = { lazyPokemonItems.retry() }) {
//                                Text("Try Again")
//                            }
//                        }
//                    }
//
//                    else -> Unit
//                }
//            }
//        }
//    }
//}