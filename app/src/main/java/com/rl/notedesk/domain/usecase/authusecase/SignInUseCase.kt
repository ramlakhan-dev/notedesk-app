package com.rl.notedesk.domain.usecase.authusecase

import com.rl.notedesk.domain.model.AuthResult
import com.rl.notedesk.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AuthResult = authRepository.signIn(email, password)
}