package com.example.newsly.domain.usecase

import com.example.newsly.domain.repository.AuthRepository

class SignOutUseCase(private val repo: AuthRepository) {
    operator fun invoke() = repo.signOut()
}