package com.rl.notedesk.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rl.notedesk.domain.model.AuthResult
import com.rl.notedesk.domain.model.OpResult
import com.rl.notedesk.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override suspend fun signUp(
        email: String,
        password: String
    ): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun sendResetPasswordLink(email: String): OpResult {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(): FirebaseUser? {
        TODO("Not yet implemented")
    }
}