package com.rl.notedesk.domain.usecase.authusecase

import com.rl.notedesk.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.signOut()
}