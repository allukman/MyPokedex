package com.karsatech.mypokedex.feature.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.karsatech.mypokedex.core.common.base.BaseViewModel
import com.karsatech.mypokedex.core.common.utils.state.UiState
import com.karsatech.mypokedex.core.common.utils.state.UiState.StateFailed
import com.karsatech.mypokedex.core.common.utils.state.UiState.StateInitial
import com.karsatech.mypokedex.core.common.utils.state.UiState.StateSuccess
import com.karsatech.mypokedex.core.data.source.local.datastore.PokedexDataStore
import com.karsatech.mypokedex.core.domain.model.User
import com.karsatech.mypokedex.core.domain.usecase.CheckEmailUseCase
import com.karsatech.mypokedex.core.domain.usecase.LoginUserUseCase
import com.karsatech.mypokedex.core.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataStore: PokedexDataStore,
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val checkEmailUseCase: CheckEmailUseCase
) : BaseViewModel() {

    private val _authState = MutableStateFlow<UiState<User>>(StateInitial)
    val authState = _authState.asStateFlow()

    fun register(user: User) = viewModelScope.launch {
        try {
            _authState.value = UiState.StateLoading
            delay(1500)

            val isEmailExist = checkEmailUseCase(user.email)

            if (isEmailExist) {
                _authState.value =
                    StateFailed(Throwable("Email already registered"))
                return@launch
            }

            registerUserUseCase(user)

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

            val user = loginUserUseCase(email, password)

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