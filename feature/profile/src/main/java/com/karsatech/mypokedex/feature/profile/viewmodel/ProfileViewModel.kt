package com.karsatech.mypokedex.feature.profile.viewmodel

import androidx.lifecycle.viewModelScope
import com.karsatech.mypokedex.core.common.base.BaseViewModel
import com.karsatech.mypokedex.core.common.utils.state.UiState
import com.karsatech.mypokedex.core.common.utils.state.UiState.StateFailed
import com.karsatech.mypokedex.core.common.utils.state.UiState.StateSuccess
import com.karsatech.mypokedex.core.data.repository.PokedexRepository
import com.karsatech.mypokedex.core.data.source.local.datastore.PokedexDataStore
import com.karsatech.mypokedex.core.data.source.local.model.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: PokedexRepository,
    private val dataStore: PokedexDataStore
) : BaseViewModel() {

    private val _profileState = MutableStateFlow<UiState<UserEntity>>(UiState.StateInitial)
    val profileState = _profileState .asStateFlow()

    fun getProfile() = viewModelScope.launch {
        repository.getUser().collect { user ->
            if (user != null) {
                _profileState.value = StateSuccess(user)
            } else {
                _profileState.value = StateFailed(Throwable("User not found"))
            }
        }
    }

    fun logout() = viewModelScope.launch {
        dataStore.clearToken()
        _profileState.value = UiState.StateInitial
    }
}