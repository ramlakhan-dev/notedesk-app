package com.rl.notedesk.domain.usecase.authusecase

import com.google.firebase.auth.FirebaseUser
import com.rl.notedesk.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUserCase @Inject constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(): FirebaseUser? = authRepository.getCurrentUser()
}