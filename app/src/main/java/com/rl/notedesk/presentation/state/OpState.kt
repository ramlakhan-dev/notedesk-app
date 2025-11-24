package com.rl.notedesk.presentation.state

sealed class OpState {
    object Idle: OpState()
    object Loading: OpState()
    object Success: OpState()
    data class Error(val message: String): OpState()
}