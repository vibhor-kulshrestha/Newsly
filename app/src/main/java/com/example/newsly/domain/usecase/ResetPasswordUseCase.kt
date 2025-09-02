package com.example.newsly.domain.usecase

import com.example.newsly.domain.repository.AuthRepository
import com.example.newsly.domain.repository.AuthResult

class ResetPasswordUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String) : AuthResult = repo.resetPassword(email)

}