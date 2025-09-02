package com.example.newsly.di

import com.example.newsly.data.repository.FirebaseAuthRepository
import com.example.newsly.domain.repository.AuthRepository
import com.example.newsly.domain.usecase.ObserveAuthStateUseCase
import com.example.newsly.domain.usecase.ResetPasswordUseCase
import com.example.newsly.domain.usecase.SignInUseCase
import com.example.newsly.domain.usecase.SignOutUseCase
import com.example.newsly.domain.usecase.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository =
        FirebaseAuthRepository(auth)

    @Provides
    fun provideSignInUseCase(repo: AuthRepository) = SignInUseCase(repo)

    @Provides
    fun provideSignUpUseCase(repo: AuthRepository) = SignUpUseCase(repo)

    @Provides
    fun provideObserveAuthStateUseCase(repo: AuthRepository) =
        ObserveAuthStateUseCase(repo)

    @Provides
    fun provideSignOutUseCase(repo: AuthRepository) = SignOutUseCase(repo)

    @Provides
    fun provideResetPasswordUseCase(repo: AuthRepository) = ResetPasswordUseCase(repo)
}