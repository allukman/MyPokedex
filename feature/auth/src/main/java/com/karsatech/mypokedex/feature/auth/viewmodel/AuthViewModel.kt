package com.karsatech.mypokedex.feature.auth.viewmodel

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
class AuthViewModel @Inject constructor(
    private val dataStore: PokedexDataStore,
    private val repository: PokedexRepository
) : BaseViewModel() {

    private val _authState = MutableStateFlow<UiState<UserEntity>>(UiState.StateInitial)
    val authState = _authState.asStateFlow()

    fun register(userEntity: UserEntity) = viewModelScope.launch {
        repository.registerUser(userEntity)
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _authState.value = UiState.StateLoading
        val user = repository.loginUser(email, password)
        user?.let {
            dataStore.saveToken(it.token)
            _authState.value = StateSuccess(it)
        } ?: run {
            _authState.value = StateFailed(Throwable("Invalid email or password"))
        }
    }

    fun resetAuthState() {
        _authState.value = UiState.StateInitial
    }

}