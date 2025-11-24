package com.rl.notedesk.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.rl.notedesk.domain.usecase.authusecase.GetCurrentUserUserCase
import com.rl.notedesk.domain.usecase.authusecase.SignOutUseCase
import com.rl.notedesk.presentation.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    getCurrentUserUserCase: GetCurrentUserUserCase,
    private val signOutUseCase: SignOutUseCase
): ViewModel() {

    private val _userState = MutableStateFlow<AuthState>(AuthState.Idle)
    val userState: StateFlow<AuthState> = _userState.asStateFlow()

    private val _signOutState = MutableStateFlow<AuthState>(AuthState.Idle)
    val signOutState: StateFlow<AuthState> = _signOutState.asStateFlow()

    init {
        val user = getCurrentUserUserCase()
        if (user != null) {
            _userState.value = AuthState.Authenticated
        } else {
            _userState.value = AuthState.Unauthenticated
        }
    }

    fun signOut() {
        signOutUseCase()
        _signOutState.value = AuthState.Unauthenticated
    }
}