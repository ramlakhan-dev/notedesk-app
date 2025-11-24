package com.rl.notedesk.presentation.screen.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rl.notedesk.domain.model.AuthResult
import com.rl.notedesk.domain.usecase.authusecase.SignInUseCase
import com.rl.notedesk.presentation.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
): ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            val result = signInUseCase(email, password)
            _authState.value = when(result) {
                is AuthResult.Authenticated -> AuthState.Authenticated
                is AuthResult.Unauthenticated -> AuthState.Unauthenticated
                is AuthResult.Error -> AuthState.Error(result.message)
            }
        }
    }
}