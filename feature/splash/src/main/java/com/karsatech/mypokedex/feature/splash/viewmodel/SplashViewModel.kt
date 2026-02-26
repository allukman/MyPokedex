package com.karsatech.mypokedex.feature.splash.viewmodel

import androidx.lifecycle.viewModelScope
import com.karsatech.mypokedex.core.common.base.BaseViewModel
import com.karsatech.mypokedex.core.data.source.local.datastore.PokedexDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    dataStore: PokedexDataStore
) : BaseViewModel() {
    val token = dataStore.getToken.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(),
        initialValue = 0
    )
}