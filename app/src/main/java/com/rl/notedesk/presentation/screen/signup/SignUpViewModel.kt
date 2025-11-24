package com.rl.notedesk.presentation.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rl.notedesk.domain.model.AuthResult
import com.rl.notedesk.domain.usecase.authusecase.SignUpUseCase
import com.rl.notedesk.presentation.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
 private val signUpUseCase: SignUpUseCase
): ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            val result = signUpUseCase(email, password)
            _authState.value = when(result) {
                is AuthResult.Authenticated -> AuthState.Authenticated
                is AuthResult.Unauthenticated -> AuthState.Unauthenticated
                is AuthResult.Error -> AuthState.Error(result.message)
            }
        }
    }
}