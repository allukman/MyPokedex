package com.karsatech.mypokedex.feature.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.karsatech.mypokedex.core.common.base.BaseViewModel
import com.karsatech.mypokedex.core.navigation.route.HomeGraph.HomeDataTypeRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel() {
    val args by lazy { savedStateHandle.toRoute<HomeDataTypeRoute>() }
}