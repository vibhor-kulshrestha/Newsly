package com.example.newsly.domain.usecase

import com.example.newsly.domain.repository.AuthRepository

class SignUpUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String, name: String?) =
        repo.signUp(email, password, name)
}