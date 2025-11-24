package com.rl.notedesk.di

import com.google.firebase.auth.FirebaseAuth
import com.rl.notedesk.data.repository.AuthRepositoryImpl
import com.rl.notedesk.domain.repository.AuthRepository
import com.rl.notedesk.domain.usecase.authusecase.ResetPasswordUseCase
import com.rl.notedesk.domain.usecase.authusecase.SignInUseCase
import com.rl.notedesk.domain.usecase.authusecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase = SignUpUseCase(authRepository)

    @Provides
    @Singleton
    fun provideSignInUseCase(authRepository: AuthRepository): SignInUseCase = SignInUseCase(authRepository)

    @Provides
    @Singleton
    fun provideResetPasswordUseCase(authRepository: AuthRepository): ResetPasswordUseCase =
        ResetPasswordUseCase(authRepository)
}