package com.rl.notedesk.domain.model

sealed class OpResult {
    object Success: OpResult()
    data class Error(val message: String): OpResult()
}