package com.rl.notedesk.domain.usecase.authusecase

import com.rl.notedesk.domain.model.OpResult
import com.rl.notedesk.domain.repository.AuthRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): OpResult = authRepository.sendResetPasswordLink(email)
}