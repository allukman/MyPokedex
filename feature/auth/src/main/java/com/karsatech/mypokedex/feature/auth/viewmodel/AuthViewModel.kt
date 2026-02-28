package com.karsatech.mypokedex.feature.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.karsatech.mypokedex.core.common.base.BaseViewModel
import com.karsatech.mypokedex.core.common.utils.state.UiState
import com.karsatech.mypokedex.core.common.utils.state.UiState.StateFailed
import com.karsatech.mypokedex.core.common.utils.state.UiState.StateInitial
import com.karsatech.mypokedex.core.common.utils.state.UiState.StateSuccess
import com.karsatech.mypokedex.core.data.repository.PokedexRepository
import com.karsatech.mypokedex.core.data.source.local.datastore.PokedexDataStore
import com.karsatech.mypokedex.core.data.source.local.model.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataStore: PokedexDataStore,
    private val repository: PokedexRepository
) : BaseViewModel() {

    private val _authState = MutableStateFlow<UiState<UserEntity>>(StateInitial)
    val authState = _authState.asStateFlow()

    fun register(user: UserEntity) = viewModelScope.launch {
        try {
            _authState.value = UiState.StateLoading
            delay(1500)
            val isEmailExist = repository.isEmailExist(user.email)
            if (isEmailExist) {
                _authState.value =
                    StateFailed(Throwable("Email already registered"))
                return@launch
            }

            repository.registerUser(user)
            _authState.value = StateSuccess(user)

        } catch (e: Exception) {
            _authState.value = StateFailed(e)
        }
    }

    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        try {
            _authState.value = UiState.StateLoading
            delay(1500)
            val user = repository.loginUser(email, password)

            if (user != null) {
                val token = System.currentTimeMillis() / 1000
                dataStore.saveToken(token.toInt() + 600)
                _authState.value = StateSuccess(user)
            } else {
                _authState.value =
                    StateFailed(Throwable("Invalid email or password"))
            }

        } catch (e: Exception) {
            _authState.value = StateFailed(e)
        }
    }

    fun resetAuthState() {
        _authState.value = StateInitial
    }
}