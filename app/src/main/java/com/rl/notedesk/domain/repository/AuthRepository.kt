package com.rl.notedesk.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.rl.notedesk.domain.model.AuthResult
import com.rl.notedesk.domain.model.OpResult

interface AuthRepository {

    suspend fun signUp(email: String, password: String): AuthResult

    suspend fun signIn(email: String, password: String): AuthResult

    suspend fun sendResetPasswordLink(email: String): OpResult

    fun signOut()

    fun getCurrentUser(): FirebaseUser?
}