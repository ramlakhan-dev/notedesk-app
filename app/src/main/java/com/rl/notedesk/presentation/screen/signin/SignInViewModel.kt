package com.rl.notedesk.presentation.screen.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rl.notedesk.domain.model.AuthResult
import com.rl.notedesk.domain.model.OpResult
import com.rl.notedesk.domain.usecase.authusecase.ResetPasswordUseCase
import com.rl.notedesk.domain.usecase.authusecase.SignInUseCase
import com.rl.notedesk.presentation.state.AuthState
import com.rl.notedesk.presentation.state.OpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
): ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _opState = MutableStateFlow<OpState>(OpState.Idle)
    val opState: StateFlow<OpState> = _opState.asStateFlow()

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

    fun sendResetPasswordLink(email: String) {
        viewModelScope.launch {
            _opState.value = OpState.Loading

            val result = resetPasswordUseCase(email)
            _opState.value = when(result) {
                is OpResult.Success -> OpState.Success
                is OpResult.Error -> OpState.Error(result.message)
            }
        }
    }
}