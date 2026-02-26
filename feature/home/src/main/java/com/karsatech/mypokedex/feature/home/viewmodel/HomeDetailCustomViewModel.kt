package com.karsatech.mypokedex.feature.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.karsatech.mypokedex.core.common.base.BaseViewModel
import com.karsatech.mypokedex.core.navigation.route.HomeGraph.HomeDataClassRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeDetailCustomViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val args by lazy {
        savedStateHandle.toRoute<HomeDataClassRoute>(HomeDataClassRoute.typeMap)
    }
}