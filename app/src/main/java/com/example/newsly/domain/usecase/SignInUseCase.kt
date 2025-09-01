package com.example.newsly.domain.usecase

import com.example.newsly.domain.repository.AuthRepository

class SignInUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) = repo.signIn(email, password)
}