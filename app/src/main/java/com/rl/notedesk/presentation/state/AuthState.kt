package com.rl.notedesk.presentation.state

sealed class AuthState {
    object Idle: AuthState()
    object Loading: AuthState()
    object Authenticated: AuthState()
    object Unauthenticated: AuthState()
    data class Error(val message: String): AuthState()
}