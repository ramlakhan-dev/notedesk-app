package com.rl.notedesk.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rl.notedesk.domain.model.AuthResult
import com.rl.notedesk.domain.model.OpResult
import com.rl.notedesk.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override suspend fun signUp(
        email: String,
        password: String
    ): AuthResult {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Authenticated
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Sign Up failed")
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): AuthResult {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Authenticated
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Sign In failed")
        }
    }

    override suspend fun sendResetPasswordLink(email: String): OpResult {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            OpResult.Success
        } catch (e: Exception) {
            OpResult.Error(e.message ?: "Failed to send the link")
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}